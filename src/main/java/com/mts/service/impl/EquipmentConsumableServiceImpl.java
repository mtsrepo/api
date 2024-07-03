package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.service.EquipmentConsumableService;

@Service
public class EquipmentConsumableServiceImpl implements EquipmentConsumableService {
	
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
}
