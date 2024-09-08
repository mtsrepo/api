package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mts.entity.MtsEquipmentMaster;

public interface AnalyticService {

	Map<String, List<MtsEquipmentMaster>> getAnalyticsBylocationType();

}
