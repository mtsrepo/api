package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SaveAssetReq;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.service.EquipmentAssetService;

@Service
public class EquipmentAssetServiceImpl implements EquipmentAssetService {
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentTypeMasterRepository mtsEquipmentTypeMasterRepository;

	@Override
	public JSONObject getAllAssets() {
		JSONObject result = new JSONObject();
		try {
			List<MtsEquipmentMaster> data = mtsEquipmentMasterRepository.getAllAssets();
//			result = new JSONObject(data);
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public List<Map<String, Object>> getAssetTypeIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsEquipmentTypeMasterRepository.getAssetTypeIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public JSONObject saveAsset(SaveAssetReq asstReq) {
		JSONObject result = new JSONObject();
		try {
			MtsEquipmentMaster asset = new MtsEquipmentMaster();
			asset.setMtsEquipTypeMasterId(asstReq.getMtsEquipTypeMasterId());
			asset.setName(asstReq.getAssetName());
			asset.setManufacturedCompany(asstReq.getManufacturedCompany());
			asset.setSuppliedCompany(asstReq.getSuppliedCompany());
			asset.setDescription(asstReq.getDescription());
			asset.setDateOfPurchase(asstReq.getDateOfPurchase());
			asset.setLastDateOfWarranty(asstReq.getLastDateOfWarranty());
			
			mtsEquipmentMasterRepository.saveAndFlush(asset);
			
			result.put("message", "Asset saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
