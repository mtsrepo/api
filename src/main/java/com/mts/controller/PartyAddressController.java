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

import com.mts.dataObjects.SavePartAddReq;
import com.mts.service.PartyAddressService;
import com.mts.util.JwtUtil;

@Controller
public class PartyAddressController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	PartyAddressService partyAddressService;


	@PostMapping("/savePartyAddress")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> savePartyAddress(@RequestBody SavePartAddReq partAddReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(partAddReq.getAuthToken(), partAddReq.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = partyAddressService.savePartyAddress(partAddReq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/partyAddressDashboard")
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

			returnMap = partyAddressService.getAllPartyAddresses();
			returnMap.put("status", 1);
		} catch (Exception e) {
			returnMap.put("message", "party address fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/partyIdWiseAddress")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> partyIdWiseAddress(@RequestBody HashMap<String, String> partyReq) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = partyReq.get("authToken");
			String userId = partyReq.get("userId");
			Long mtsPartyMasterId = Long.valueOf(partyReq.get("mtsPartyMasterId"));

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			List<Map<String, Object>> partyMasterIdAddress = partyAddressService
					.getPartyMasterIdAddress(mtsPartyMasterId);

			returnMap.put("message", partyMasterIdAddress);
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "party id address not found");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

}
