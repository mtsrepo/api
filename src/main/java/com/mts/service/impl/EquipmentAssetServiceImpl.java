package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.entity.MtsEquipmentTypeMaster;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.service.EquipmentAssetService;

@Service
public class EquipmentAssetServiceImpl implements EquipmentAssetService {
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentTypeMasterRepository mtsEquipmentTypeMasterRepository;

	@Override
	public JSONObject getAllAssets(String category) {
		JSONObject result = new JSONObject();
		try {
			List<MtsEquipmentTypeMaster> data = mtsEquipmentTypeMasterRepository.findByCategory(category);
//			result = new JSONObject(data);
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAllConsumables(String category) {
		JSONObject result = new JSONObject();
		try {
			List<MtsEquipmentTypeMaster> data = mtsEquipmentTypeMasterRepository.findByCategory(category);
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAssetTypeIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsEquipmentTypeMasterRepository.getAssetTypeIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
