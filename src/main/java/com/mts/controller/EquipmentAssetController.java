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

import com.mts.dataObjects.SaveAssetReq;
import com.mts.service.EquipmentAssetService;
import com.mts.util.JsonUtil;
import com.mts.util.JwtUtil;

@Controller
public class EquipmentAssetController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	EquipmentAssetService equipmentAssetService;

	
	@PostMapping("/assetTypeData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> assetTypeData(@RequestBody HashMap<String, String> equReq) {
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
			
			List<Map<String, Object>> assetTypeData = equipmentAssetService.getAssetTypeIdName();
			
			returnMap.put("message", JsonUtil.toJsonArrayOfObjects(assetTypeData));
			returnMap.put("status", 1);
			
		} catch (Exception e) {
			returnMap.put("message", "assets type not found");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/saveAsset")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveAsset(@RequestBody SaveAssetReq asstReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(asstReq.getAuthToken(), asstReq.getUserId());
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = equipmentAssetService.saveAsset(asstReq);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/assetsDashboard")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> assetsDashboard(@RequestBody HashMap<String, String> equReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = equReq.get("authToken");
			String userId = equReq.get("userId");
//			String category = equReq.get("category");
			
			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = equipmentAssetService.getAllAssets();
			returnMap.put("status", 1);
			
		} catch (Exception e) {
			returnMap.put("message", "assets fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/locationData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> locationData(@RequestBody HashMap<String, String> locReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = locReq.get("authToken");
			String userId = locReq.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			List<Map<String, Object>> locationMasterIdName = equipmentAssetService.getLocationMasterIdName();

			returnMap.put("message", JsonUtil.toJsonArrayOfObjects(locationMasterIdName));
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "location id name not found");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/deleteAsset")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> deleteAsset(@RequestBody HashMap<String, String> asset) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = asset.get("authToken");
			String userId = asset.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			Long mtsEquipMasterId = Long.valueOf(asset.get("mtsEquipMasterId"));
			returnMap = equipmentAssetService.deleteAsset(mtsEquipMasterId);

		} catch (Exception e) {
			returnMap.put("message", "asset delete error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

}
