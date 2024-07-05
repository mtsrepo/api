package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			List<MtsEquipmentMaster> data = mtsEquipmentMasterRepository.getAllConsumables();
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject saveConsumable(SaveConsumableReq consReq) {
		JSONObject result = new JSONObject();
		try {
			MtsEquipmentMaster consumable = new MtsEquipmentMaster();
			consumable.setMtsEquipTypeMasterId(consReq.getMtsEquipTypeMasterId());
			consumable.setName(consReq.getConsumableName());
			consumable.setManufacturedCompany(consReq.getManufacturedCompany());
			consumable.setSuppliedCompany(consReq.getSuppliedCompany());
			consumable.setDescription(consReq.getDescription());
			consumable.setDateOfPurchase(consReq.getDateOfPurchase());
			consumable.setLastDateOfWarranty(consReq.getLastDateOfWarranty());
			
			mtsEquipmentMasterRepository.saveAndFlush(consumable);
			
			result.put("message", "Consumable saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
