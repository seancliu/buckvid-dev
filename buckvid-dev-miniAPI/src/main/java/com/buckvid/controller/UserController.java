package com.buckvid.controller;

import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.pojo.Reports;
import com.buckvid.pojo.vo.BuckvidUsersVO;
import com.buckvid.pojo.vo.CreatorVideo;
import com.buckvid.service.UserService;
import com.buckvid.utils.BuckvidJSONResult;
import com.buckvid.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@Api(value = "User-related API", tags = "User-related API")
@RequestMapping("/user")
public class UserController extends BasicController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "UploadIcon", notes = "UploadIcon API")
	@ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "String", paramType = "query")
	@PostMapping("/uploadicon")
	public BuckvidJSONResult uploadIcon(String userId, @RequestParam("file")MultipartFile[] files) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return BuckvidJSONResult.errorMsg("User ID cannot be empty");
		}

		// Data dir
		String fileSpace = "C:/buckvid_dev";

		// dir in database
		String uploadPathDb = "/" + userId + "/icon";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					String finalIconPath = fileSpace + uploadPathDb + "/" + fileName;

					uploadPathDb += "/" + fileName;

					File outputFile = new File(finalIconPath);
					if (outputFile.getParentFile() != null || !outputFile.getParentFile().isDirectory()) {
						// create parent folder
						outputFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outputFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);

				}
			} else {
				return BuckvidJSONResult.errorMsg("Uploading Error");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return BuckvidJSONResult.errorMsg("Uploading Error");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}

		BuckvidUsers user = new BuckvidUsers();
		user.setId(userId);
		user.setIcon(uploadPathDb);
		userService.updateUserInfo(user);
		return BuckvidJSONResult.ok(uploadPathDb);
	}

    @ApiOperation(value = "QueryUserInfo", notes = "QueryUserInfo API")
    @ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "String", paramType = "query")
    @PostMapping("/query")
    public @ResponseBody BuckvidJSONResult query(String userId, String followerId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return BuckvidJSONResult.errorMsg("User ID cannot be empty");
        }

        BuckvidUsers userInfo = userService.queryUserInfo(userId);
        BuckvidUsersVO userVO = new BuckvidUsersVO();
        BeanUtils.copyProperties(userInfo, userVO);


        userVO.setFollowed(userService.queryIfFollowed(userId, followerId));

        return BuckvidJSONResult.ok(userVO);
    }

	@PostMapping("/queryCreator")
	public BuckvidJSONResult queryPublisher(String currUserId, String videoId,
										  String creatorUserId) throws Exception {

		if (StringUtils.isBlank(creatorUserId)) {
			return BuckvidJSONResult.errorMsg("");
		}

		// 1. query creator's info
		BuckvidUsers userInfo = userService.queryUserInfo(creatorUserId);
		BuckvidUsersVO creator = new BuckvidUsersVO();
		BeanUtils.copyProperties(userInfo, creator);

		// 2. query the relationship between current user and the video
		boolean userLikesVideo = userService.isUserLikedVideo(currUserId, videoId);

		CreatorVideo bean = new CreatorVideo();
		bean.setCreator(creator);
		bean.setUserLikesVideo(userLikesVideo);

		return BuckvidJSONResult.ok(bean);
	}

	@PostMapping("/follow")
	public BuckvidJSONResult follow(String userId, String followerId) throws Exception {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(followerId)) {
			return BuckvidJSONResult.errorMsg("");
		}
		userService.saveUserFollowerRelationship(userId, followerId);
		return BuckvidJSONResult.ok("Followed successfully");
	}

	@PostMapping("/unfollow")
	public BuckvidJSONResult unfollow(String userId, String followerId) throws Exception {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(followerId)) {
			return BuckvidJSONResult.errorMsg("");
		}
		userService.deleteUserFollowerRelationship(userId, followerId);
		return BuckvidJSONResult.ok("Unfollowed");
	}

	@PostMapping("/report")
	public BuckvidJSONResult report(@RequestBody Reports report) {
		userService.report(report);
		return BuckvidJSONResult.ok("Report submitted");
	}
}
