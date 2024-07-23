package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SaveConsumableReq;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.service.EquipmentConsumableService;

@Service
public class EquipmentConsumableServiceImpl implements EquipmentConsumableService {
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentTypeMasterRepository mtsEquipmentTypeMasterRepository;
	
	
	@Override
	public List<Map<String, Object>> getConsumableTypeIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsEquipmentTypeMasterRepository.getConsumableTypeIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public JSONObject getAllConsumables() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsEquipmentMasterRepository.getAllConsumables();
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject saveConsumable(SaveConsumableReq consReq) {
		JSONObject result = new JSONObject();
		Long userId = 0L;
		try {
			MtsEquipmentMaster consumable = new MtsEquipmentMaster();
			Random random = new Random();
			int fiveDigitNumber = 10000 + random.nextInt(90000);
			long currentTimeSeconds = System.currentTimeMillis() / 1000;
	        
	        String code = "MCON" + fiveDigitNumber;
	        userId = ((new Date().getTime() * 10) + (long) (Math.floor(Math.random() * 90L) + 100L));
			
	        consumable.setMtsEquipMasterId(currentTimeSeconds + fiveDigitNumber);
	        consumable.setMtsEquipMasterCode(code);
			
			consumable.setMtsEquipTypeMasterId(consReq.getMtsEquipTypeMasterId());
			consumable.setMtsEquipName(consReq.getConsumableName());
			consumable.setManufacturedCompany(consReq.getManufacturedCompany());
			consumable.setSuppliedCompany(consReq.getSuppliedCompany());
			consumable.setDescription(consReq.getDescription());
			consumable.setDateOfPurchase(consReq.getDateOfPurchase());
			consumable.setLastDateOfWarranty(consReq.getLastDateOfWarranty());
			
			mtsEquipmentMasterRepository.saveAndFlush(consumable);
			
			result.put("message", "Consumable saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "consumable save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
}
