package com.mts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.dataObjects.SaveConsumableReq;
import com.mts.service.EquipmentConsumableService;
import com.mts.util.JsonUtil;
import com.mts.util.JwtUtil;

@Controller
public class EquipmentConsumableController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	EquipmentConsumableService equipmentConsumableService;
	
	
	@PostMapping("/consumableTypeData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> consumableTypeData(@RequestBody HashMap<String, String> equReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = equReq.get("authToken");
			String userId = equReq.get("userId");
			
			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			List<Map<String, Object>> consumableTypeData = equipmentConsumableService.getConsumableTypeIdName();
			
			returnMap.put("message", JsonUtil.toJsonArrayOfObjects(consumableTypeData));
			returnMap.put("status", 1);
			
		} catch (Exception e) {
			returnMap.put("message", "assets type not found");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/saveConsumable")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveConsumable(@RequestBody SaveConsumableReq consReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(consReq.getAuthToken(), consReq.getUserId());
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = equipmentConsumableService.saveConsumable(consReq);
			
		}catch (Exception e) {
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
//			String category = equReq.get("category");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = equipmentConsumableService.getAllConsumables();
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "consumables fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
}
