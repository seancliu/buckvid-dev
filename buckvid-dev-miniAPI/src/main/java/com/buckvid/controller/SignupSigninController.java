package com.buckvid.controller;

import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.service.UserService;
import com.buckvid.utils.BuckvidJSONResult;
import com.buckvid.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "SignUp/SignIn API", tags = "SignUp/SignIn Controller")
public class SignupSigninController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "SignUp", notes = "SignUp API")
	@PostMapping("/signup")
	public BuckvidJSONResult signUp(@RequestBody BuckvidUsers user) throws Exception {
		// 1. check if username or password is BLANK
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return BuckvidJSONResult.errorMsg("Username or password cannot be blank.");
		}

		// 2. check if username is available
		if (userService.isUsernameExistent(user.getUsername())) {
			return BuckvidJSONResult.errorMsg("That username is taken. Try another.");
		} else {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFollowersCount(0);
			user.setFollowingCount(0);
			user.setLikeReceivedCount(0);
			userService.saveUser(user);
		}

		user.setPassword(""); // avoid password leaks
		return BuckvidJSONResult.ok(user);
	}

	@ApiOperation(value = "SignIn", notes = "SignIn API")
	@PostMapping("/signin")
	public BuckvidJSONResult signIn(@RequestBody BuckvidUsers user) throws Exception {
		// 1. check if username or password is BLANK
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return BuckvidJSONResult.errorMsg("Username or password cannot be blank.");
		}

		// 2. check if a matched user can be found
		BuckvidUsers result = userService.queryForSignIn(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));

		if (result != null) {
			result.setPassword("");
			return BuckvidJSONResult.ok(result);
		} else {
			return BuckvidJSONResult.errorMsg("The username and/or password you specified are not correct.");
		}
	}
}
