package com.buckvid.controller;

import com.buckvid.service.BgmService;
import com.buckvid.utils.BuckvidJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "BGM API", tags = {"BGM Controller"})
@RequestMapping("/bgm")
public class BgmController {
	@Autowired
	private BgmService bgmService;

	@ApiOperation(value = "Query BGM List", notes = "Query BGM List API")
	@PostMapping("/list")
	public BuckvidJSONResult list() {
		return BuckvidJSONResult.ok(bgmService.queryBgmList());
	}
	
}
