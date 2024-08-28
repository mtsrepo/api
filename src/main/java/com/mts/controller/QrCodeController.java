package com.mts.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mts.dataObjects.ScanQrReq;
import com.mts.service.QrCodeService;
import com.mts.util.JwtUtil;

@Controller
public class QrCodeController {

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	QrCodeService qrCodeService;

	@PostMapping("/getScannedQrData")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> getScannedQrData(@RequestBody ScanQrReq qrReq) {
		JSONObject returnMap = new JSONObject();
		try {
			boolean tokenVerified = jwtUtil.validateToken(qrReq.getAuthToken(), qrReq.getUserId());
			if (!tokenVerified) {
				returnMap.put("status", 0);
				returnMap.put("message", "invalid token");
				return returnMap.toMap();
			}
			returnMap = qrCodeService.getResponseFromScannedQR(qrReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap.toMap();
	}
}
