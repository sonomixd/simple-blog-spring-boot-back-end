package com.sonomixd.simpleblogspringbootandangular.controller;

import com.sonomixd.simpleblogspringbootandangular.dto.LoginRequest;
import com.sonomixd.simpleblogspringbootandangular.dto.RegisterRequest;
import com.sonomixd.simpleblogspringbootandangular.service.AuthService;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;


	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity signup(@RequestParam("userName") String userName, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("file") MultipartFile file) {
		RegisterRequest registerRequest = new RegisterRequest();
		String rootPath = null;
		registerRequest.setUsername(userName);
		registerRequest.setEmail(email);
		registerRequest.setPassword(password);
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				rootPath = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main"
						+ File.separator + "resources";
				File dir = new File(rootPath + File.separator + "profile");
				if (!dir.exists())
					dir.mkdirs();
				String fileName = new Date().getTime() + file.getOriginalFilename();
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				registerRequest.setProfile_photo(fileName);

			} catch (Exception e) {
			    e.getMessage();
			}
		}

		authService.signup(registerRequest);
		return new ResponseEntity<>("Image saved in " + rootPath, HttpStatus.OK);

	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
}
