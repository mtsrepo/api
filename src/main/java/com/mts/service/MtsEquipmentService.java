package com.mts.service;

import org.json.JSONObject;

public interface MtsEquipmentService {

	JSONObject getAllAssets(String category);

	JSONObject getAllConsumables(String category);

}
