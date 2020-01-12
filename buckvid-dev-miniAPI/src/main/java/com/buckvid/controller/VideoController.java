package com.buckvid.controller;

import com.buckvid.enums.VideoStatusEnum;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.pojo.Videos;
import com.buckvid.service.BgmService;
import com.buckvid.service.VideoService;
import com.buckvid.utils.BuckvidJSONResult;
import com.buckvid.utils.MergeVideoBgm;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

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

	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	public BuckvidJSONResult uploadIcon(String userId,
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
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalVideoPath = "";
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					finalVideoPath = fileSpace + uploadPathDb + "/" + fileName;

					uploadPathDb += "/" + fileName;

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
			String videoOutputPath = fileSpace + uploadPathDb;
			tool.convertor(videoInputPath, bgmInputPath, videoDuration, videoOutputPath);
		}

		// save video info to DB
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setUserId(userId);
		video.setVideoSeconds((float)videoDuration);
		video.setVideoWidth(videoWidth);
		video.setVideoHeight(videoHeight);
		video.setVideoDesc(videoDesc);
		video.setVideoPath(uploadPathDb);
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setTimestamp(new Date());

		videoService.saveVideo(video);

		return BuckvidJSONResult.ok();
	}
	
}
