package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mts.dataObjects.SaveAssetReq;

public interface EquipmentAssetService {

	JSONObject getAllAssets();

	List<Map<String, Object>> getAssetTypeIdName();

	JSONObject saveAsset(SaveAssetReq asstReq);

	List<Map<String, Object>> getLocationMasterIdName();

}
