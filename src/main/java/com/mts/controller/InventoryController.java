package com.mts.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.dataObjects.SaveInvReq;
import com.mts.service.InventoryService;
import com.mts.util.JwtUtil;

@Controller
public class InventoryController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	InventoryService inventoryService;

	@PostMapping("/saveInventory")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveInventory(@RequestBody SaveInvReq invReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(invReq.getAuthToken(), invReq.getUserId());
			if(!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			returnMap = inventoryService.saveInventory(invReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
}
