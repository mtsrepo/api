package com.mts.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.service.MtsEquipmentService;
import com.mts.util.JwtUtil;

@Controller
@RequestMapping("/mts")
public class EquipmentController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	MtsEquipmentService mtsEquipmentService;

	@PostMapping("/assetsDashboard")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> assetsDashboard(@RequestBody HashMap<String, String> equReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = equReq.get("authToken");
			String userId = equReq.get("userId");
			String category = equReq.get("category");
			
			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = mtsEquipmentService.getAllAssets(category);
			
		} catch (Exception e) {
			returnMap.put("message", "assets fetch error");
			returnMap.put("success", false);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/consumablesDashboard")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> consumablesDashboard(@RequestBody HashMap<String, String> equReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = equReq.get("authToken");
			String userId = equReq.get("userId");
			String category = equReq.get("category");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = mtsEquipmentService.getAllConsumables(category);

		} catch (Exception e) {
			returnMap.put("message", "consumables fetch error");
			returnMap.put("success", false);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

}
