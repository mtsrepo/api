package com.mts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.entity.MtsEquipmentMaster;
import com.mts.service.AnalyticService;
import com.mts.util.JwtUtil;

@Controller
public class AnalyticsController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AnalyticService analyticService;
	
	@GetMapping("/analyticsBylocationType")
	@ResponseBody
	@CrossOrigin
	public Map<String, List<MtsEquipmentMaster>> analyticsBylocationType() {
//		JSONObject returnMap = new JSONObject();
		Map<String, List<MtsEquipmentMaster>> returnMap = new HashMap<>();
		try {
			
			returnMap = analyticService.getAnalyticsBylocationType();
			
//			System.out.println(result.get("IN TRANSIT").size());
//			returnMap.put("data", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnMap;
	}
	
}
