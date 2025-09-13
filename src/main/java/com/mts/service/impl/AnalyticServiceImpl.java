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
			List<Integer> distinctTypes = mtsLocationMasterRepository.findDistinctTypes();
			
			for (int type : distinctTypes) {
//	            List<MtsEquipmentMaster> equipments = mtsEquipmentMasterRepository.getEquipmentsByLocationType(type);
				List<Map<String, Object>> equipmentsWithChallanDetails = mtsEquipmentMasterRepository.getEquipmentsWithChallanByLocationType(type);
				String status = "";
				switch (type) {
					case 1:
						status = "WAREHOUSE";
				        break;
				    case 2:
				    	status = "IN_TRANSIT";
				        break;
				    case 3:
				    	status = "PROJECT_SITE";
				        break;
				}
				result.put(status, equipmentsWithChallanDetails);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
