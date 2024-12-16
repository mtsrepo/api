package com.mts.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.dataObjects.GetChalReq;
import com.mts.dataObjects.GetTygoodReq;
import com.mts.dataObjects.SaveChalReq;
import com.mts.service.ChallanService;
import com.mts.util.JwtUtil;

@Controller
public class ChallanController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	ChallanService challanService;

	@PostMapping("/saveChallan")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveChallan(@RequestBody SaveChalReq chalReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(chalReq.getAuthToken(), chalReq.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = challanService.saveChallan(chalReq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/challanDashboard")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> challanDashboard(@RequestBody HashMap<String, String> challanReq,
			@RequestParam(defaultValue = "10") int take, @RequestParam(defaultValue = "0") int skip) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = challanReq.get("authToken");
			String userId = challanReq.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

//			returnMap = challanService.getAllChallans(take, skip);  //stopped for now
			returnMap = challanService.challanDashboard(take, skip);
			returnMap.put("status", 1);
		} catch (Exception e) {
			returnMap.put("message", "challan fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/challanDashboardOld")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> challanDashboardOld(@RequestBody HashMap<String, String> challanReq,
			@RequestParam(defaultValue = "10") int take, @RequestParam(defaultValue = "0") int skip) {
		JSONObject returnMap = new JSONObject();
		try {
			String token = challanReq.get("authToken");
			String userId = challanReq.get("userId");

			boolean tokenVerified = jwtUtil.validateToken(token, userId);
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = challanService.getAllChallans(take, skip);  //stopped for now
//			returnMap = challanService.challanDashboard(take, skip);
			returnMap.put("status", 1);
		} catch (Exception e) {
			returnMap.put("message", "challan fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

	@PostMapping("/saveRevChallan")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveRevChallan(@RequestBody SaveChalReq chalReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(chalReq.getAuthToken(), chalReq.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}

			returnMap = challanService.saveRevChallan(chalReq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/getChallanDetails")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> getChallanDetails(@RequestBody GetChalReq getchal) {
		JSONObject returnMap = new JSONObject();
//		Map<String, Object> returnMap = new HashMap<>();
		try {
			boolean tokenVerified = jwtUtil.validateToken(getchal.getAuthToken(), getchal.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = challanService.getChallanDetails(getchal);
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "challan fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
	
	@PostMapping("/getTypeWiseGoodsData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> getTypeWiseGoodsData(@RequestBody GetTygoodReq getTypeGood) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(getTypeGood.getAuthToken(), getTypeGood.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			
			returnMap = challanService.getTypeWiseGoodsData();
			returnMap.put("status", 1);

		} catch (Exception e) {
			returnMap.put("message", "goods data fetch error");
			returnMap.put("status", 0);
			e.printStackTrace();
		}
		return returnMap.toMap();
	}

}
