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

import com.mts.dataObjects.SavePartyReq;
import com.mts.service.PartyService;
import com.mts.util.JsonUtil;
import com.mts.util.JwtUtil;

@Controller
public class PartyController {

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	PartyService partyService;
	
	@PostMapping("/saveParty")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveParty(@RequestBody SavePartyReq partReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(partReq.getAuthToken(), partReq.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = partyService.saveParty(partReq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/partyDashboard")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> partyDashboard(@RequestBody HashMap<String, String> partyReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = partyReq.get("authToken");
			String userId = partyReq.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = partyService.getAllParties();
			returnMap.put("status", 1);
		} catch (Exception e) {
			returnMap.put("message", "party fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/partyMasterTypeData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> partyMasterTypeData(@RequestBody HashMap<String, String> partyReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = partyReq.get("authToken");
			String userId = partyReq.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			List<Map<String, Object>> partyMasterIdName = partyService.getPartyMasterIdName();

			returnMap.put("message", JsonUtil.toJsonArrayOfObjects(partyMasterIdName));
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "party id name not found");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
}
