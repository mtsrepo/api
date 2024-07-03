package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface EquipmentAssetService {

	JSONObject getAllAssets(String category);

	JSONObject getAllConsumables(String category);

	List<Map<String, Object>> getAssetTypeIdName();

}
