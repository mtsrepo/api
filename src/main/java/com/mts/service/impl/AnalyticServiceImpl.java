package com.mts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsLocationMaster;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.service.AnalyticService;

@Service
public class AnalyticServiceImpl implements AnalyticService{

	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	
	@Override
	public Map<String, List<Map<String, Object>>> getAnalyticsBylocationType() {
//		JSONObject result = new JSONObject();
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		try {
			List<String> distinctTypes = mtsLocationMasterRepository.findDistinctTypes();
			
			for (String type : distinctTypes) {
//	            List<MtsEquipmentMaster> equipments = mtsEquipmentMasterRepository.getEquipmentsByLocationType(type);
				List<Map<String, Object>> equipmentsWithChallanDetails = mtsEquipmentMasterRepository.getEquipmentsWithChallanByLocationType(type);
	            result.put(type, equipmentsWithChallanDetails);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
