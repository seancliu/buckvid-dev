package com.buckvid.controller;

import com.buckvid.enums.VideoStatusEnum;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.pojo.Comments;
import com.buckvid.pojo.Videos;
import com.buckvid.service.BgmService;
import com.buckvid.service.VideoService;
import com.buckvid.utils.BuckvidJSONResult;
import com.buckvid.utils.GetVideoThumb;
import com.buckvid.utils.MergeVideoBgm;
import com.buckvid.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import static com.buckvid.controller.BasicController.PAGE_SIZE;

@RestController
@Api(value = "Video API", tags = {"Video Controller"})
@RequestMapping("/video")
public class VideoController {
	@Autowired
	private BgmService bgmService;

	@Autowired
	private VideoService videoService;

	@ApiOperation(value = "UploadVideo", notes = "UploadVideo API")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "bgmId", value = "bgmId", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoDuration", value = "videoDuration", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoWidth", value = "videoWidth", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoHeight", value = "videoHeight", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "description", value = "videoWidth", required = false, dataType = "String", paramType = "form")
	})
	@PostMapping(value = "/uploadvideo", headers = "content-type=multipart/form-data")
	public BuckvidJSONResult uploadVideo(String userId,
										String bgmId,
										double videoDuration,
										int videoWidth,
										int videoHeight,
										String videoDesc,
										@ApiParam(value = "Short Vid", required = true)
										MultipartFile file) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return BuckvidJSONResult.errorMsg("User ID cannot be empty");
		}

		// Data dir
		String fileSpace = "C:/buckvid_dev";

		// dir in database
		String uploadPathDb = "/" + userId + "/video";
		String thumbPathDb = "/" + userId + "/video";

		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalVideoPath = "";
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				String fileNamePrefix = fileName.split("\\.")[0];
				if (StringUtils.isNotBlank(fileName)) {
					finalVideoPath = fileSpace + uploadPathDb + "/" + fileName;

					uploadPathDb += "/" + fileName;
					thumbPathDb += "/" + fileNamePrefix + ".jpg";

					File outputFile = new File(finalVideoPath);
					if (outputFile.getParentFile() != null || !outputFile.getParentFile().isDirectory()) {
						// create parent folder
						outputFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outputFile);
					inputStream = file.getInputStream();
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

		// check if bgm is selected
		if (StringUtils.isNotBlank(bgmId)) {
			Bgm bgm = bgmService.queryBgmById(bgmId);
			String bgmInputPath = fileSpace + bgm.getPath();

			MergeVideoBgm tool = new MergeVideoBgm("ffmpeg"); // error-prone
			String videoInputPath = finalVideoPath;
			String videoOutputName = UUID.randomUUID().toString() + ".mp4";
			uploadPathDb = "/" + userId + "/" + "video" + "/" + videoOutputName;
			finalVideoPath = fileSpace + uploadPathDb;
			String videoOutputPath = finalVideoPath;
			tool.convertor(videoInputPath, bgmInputPath, videoDuration, videoOutputPath);
		}

		// generate thumb by screenshot
		GetVideoThumb getVideoThumb = new GetVideoThumb("ffmpeg");
		getVideoThumb.getThumb(finalVideoPath, fileSpace + thumbPathDb);

		// save video info to DB
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setUserId(userId);
		video.setVideoSeconds((float)videoDuration);
		video.setVideoWidth(videoWidth);
		video.setVideoHeight(videoHeight);
		video.setVideoDesc(videoDesc);
		video.setVideoPath(uploadPathDb);
		video.setThumbnailPath(thumbPathDb);
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setTimestamp(new Date());

		String videoId = videoService.saveVideo(video);

		return BuckvidJSONResult.ok(videoId);
	}

	@ApiOperation(value = "UploadThumb", notes = "UploadThumb API")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "videoId", required = true, dataType = "String", paramType = "form")
	})
	@PostMapping(value = "/uploadthumb", headers = "content-type=multipart/form-data")
	public BuckvidJSONResult uploadThumb(String userId, String videoId, @ApiParam(value = "Video Thumb", required = true) MultipartFile file) throws Exception {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return BuckvidJSONResult.errorMsg("User ID/video ID cannot be empty");
		}

		// Data dir
		String fileSpace = "C:/buckvid_dev";

		// dir in database
		String uploadPathDb = "/" + userId + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalThumbPath = "";
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					finalThumbPath = fileSpace + uploadPathDb + "/" + fileName;

					uploadPathDb += "/" + fileName;

					File outputFile = new File(finalThumbPath);
					if (outputFile.getParentFile() != null || !outputFile.getParentFile().isDirectory()) {
						// create parent folder
						outputFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outputFile);
					inputStream = file.getInputStream();
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

		// update thumb
		videoService.updateVideo(videoId, uploadPathDb);
		return BuckvidJSONResult.ok();
	}

	/**
	 *
	 * @Description: paged display and search
	 * saveRecord：	1 - save
 * 				 	0 - don't save
	 */
	@PostMapping(value="/showAll")
	public BuckvidJSONResult showAll(@RequestBody Videos video, Integer saveRecord,
									 Integer page, Integer pageSize) throws Exception {

		if (page == null) {
			page = 1;
		}

		if (pageSize == null) {
			pageSize = PAGE_SIZE;
		}

		PagedResult result = videoService.getAllVideos(video, saveRecord, page, pageSize);
		return BuckvidJSONResult.ok(result);
	}

	/**
	 *
	 * @Description: paged display all liked videos
	 */
	@PostMapping(value="/showFavorites")
	public BuckvidJSONResult showFavorites(String userId, Integer page, Integer pageSize) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return BuckvidJSONResult.errorMsg("");
		}

		if (page == null) {
			page = 1;
		}

		if (pageSize == null) {
			pageSize = 6;
		}

		PagedResult result = videoService.getMyFavorites(userId, page, pageSize);
		return BuckvidJSONResult.ok(result);
	}

	/**
	 *
	 * @Description: paged display all liked videos
	 */
	@PostMapping(value="/showFollowing")
	public BuckvidJSONResult showFollowing(String userId, Integer page, Integer pageSize) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return BuckvidJSONResult.errorMsg("");
		}

		if (page == null) {
			page = 1;
		}

		if (pageSize == null) {
			pageSize = 6;
		}

		PagedResult result = videoService.getMyFollowing(userId, page, pageSize);
		return BuckvidJSONResult.ok(result);
	}


	/**
	 *
	 * @Description: retrieve hot trends
	 */
	@PostMapping(value="/trends")
	public BuckvidJSONResult trends() throws Exception {
		return BuckvidJSONResult.ok(videoService.getTrends());
	}

	@PostMapping(value="/userLikes")
	public BuckvidJSONResult userLikes(String userId, String videoId, String videoCreatorId) throws Exception {
		videoService.userLikesVideo(userId, videoId, videoCreatorId);
		return BuckvidJSONResult.ok();
	}

	@PostMapping(value="/userUnlikes")
	public BuckvidJSONResult userUnlikes(String userId, String videoId, String videoCreatorId) throws Exception {
		videoService.userUnlikesVideo(userId, videoId, videoCreatorId);
		return BuckvidJSONResult.ok();
	}

	@PostMapping(value="/saveComment")
	public BuckvidJSONResult saveComment(@RequestBody Comments comment, String parentCommentId, String toUserId) throws Exception {
		comment.setParentCommentId(parentCommentId);
		comment.setToUserId(toUserId);
		videoService.saveComment(comment);
		return BuckvidJSONResult.ok();
	}

	@PostMapping(value="/getVideoComments")
	public BuckvidJSONResult getVideoComments(String videoId, Integer page, Integer pageSize) throws Exception {
		if (StringUtils.isBlank((videoId))) {
			return BuckvidJSONResult.errorMsg("");
		}

		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}

		PagedResult list = videoService.getAllComments(videoId, page, pageSize);
		return BuckvidJSONResult.ok(list);
	}
}
