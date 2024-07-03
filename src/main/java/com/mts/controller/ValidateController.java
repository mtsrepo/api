package com.mts.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.service.MtsUserService;
import com.mts.util.JwtUtil;

@Controller
@RequestMapping("/mts")
public class ValidateController {

	@Autowired
	MtsUserService mtsUserService;
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/validate")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> validate(@RequestBody HashMap<String, String> loginReq) {
		JSONObject returnMap = new JSONObject();

		try {
			String idEmailContact = loginReq.get("idEmailContact");
			String password = loginReq.get("password");
			JSONObject userDetails = mtsUserService.getUserDetails(idEmailContact);

			if (userDetails == null || userDetails.isEmpty()) {
				returnMap.put("success", false);
				returnMap.put("message", "user not found");
				return returnMap.toMap();
			}

			String encryptedPass = mtsUserService.getMd5Pass(password);

			if (!userDetails.get("password").equals(encryptedPass)) {
				returnMap.put("success", false);
				returnMap.put("message", "incorrect password");
				return returnMap.toMap();
			}

			userDetails.remove("password");

			String token = jwtUtil.generateToken(String.valueOf(userDetails.get("mtsUserId")));

			userDetails.put("token", token);

			returnMap.put("message", userDetails);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("message", "User sign-in error");
			returnMap.put("success", false);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	// for testing
	@GetMapping("/hello")
	@ResponseBody
	@CrossOrigin
	public String helloWorld() {
		return "Hello MTS users";
	}
}
