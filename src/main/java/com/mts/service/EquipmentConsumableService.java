package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mts.dataObjects.SaveConsumableReq;

public interface EquipmentConsumableService {

	List<Map<String, Object>> getConsumableTypeIdName();
	
	JSONObject getAllConsumables();

	JSONObject saveConsumable(SaveConsumableReq consReq);

}
