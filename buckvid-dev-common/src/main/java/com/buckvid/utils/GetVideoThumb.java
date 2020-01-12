package com.buckvid.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 
 * @Description: 获取视频的信息
 */
public class GetVideoThumb {
	// 视频路径
	private String ffmpegEXE;

	public void getThumb(String videoInputPath, String thumbOutputPath) throws IOException, InterruptedException {
		//command example:	ffmpeg.exe -ss 00:00:01 -i spring.mp4 -vframes 1 bb.jpg
		List<String> command = new java.util.ArrayList<String>();
		command.add(ffmpegEXE);
		
		// Screenshot the first sec
		command.add("-ss");
		command.add("00:00:01");
				
		command.add("-y");
		command.add("-i");
		command.add(videoInputPath);

		// Screenshot the first frame
		command.add("-vframes");
		command.add("1");
		
		command.add(thumbOutputPath);
		
		for (String c : command) {
			System.out.print(c + " ");
		}
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
	}

	public String getFfmpegEXE() {
		return ffmpegEXE;
	}

	public void setFfmpegEXE(String ffmpegEXE) {
		this.ffmpegEXE = ffmpegEXE;
	}

	public GetVideoThumb() {
		super();
	}

	public GetVideoThumb(String ffmpegEXE) {
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public static void main(String[] args) {
		// ffmpeg command header
		GetVideoThumb videoInfo = new GetVideoThumb("ffmpeg");
		try {
			videoInfo.getThumb("C:\\buckvid_dev\\200102HB0908X2NC\\video\\tmp_57c940823485dd663b36e47882464e68.mp4","C:\\buckvid_dev\\200102HB0908X2NC\\video\\thumb_test.jpg");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}