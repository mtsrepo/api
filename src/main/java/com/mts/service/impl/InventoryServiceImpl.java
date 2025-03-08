package com.mts.service.impl;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.EquiReq;
import com.mts.dataObjects.InvReq;
import com.mts.dataObjects.SaveInvReq;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsInventoryTransaction;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsInventoryTransactionRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.service.InventoryService;
import com.mts.util.JsonUtil;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	MtsInventoryTransactionRepository mtsInventoryTransactionRepository;
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;

	@Override
	public JSONObject saveInventory(SaveInvReq invReq) {
		JSONObject result = new JSONObject();
		MtsEquipmentMaster equipment = null;
		try {
			MtsInventoryTransaction inventoryTransaction = new MtsInventoryTransaction();
			inventoryTransaction.setMtsEquipMasterId(invReq.getMtsEquipMasterId());
			inventoryTransaction.setFromLocationId(invReq.getFromLocationId());
			inventoryTransaction.setToLocationId(invReq.getToLocationId());
			inventoryTransaction.setIsActive(1);
			inventoryTransaction.setCreatedBy(Long.valueOf(invReq.getUserId()));
			inventoryTransaction.setCreatedOn(invReq.getCurrentDate());

			mtsInventoryTransactionRepository.saveAndFlush(inventoryTransaction);

			if (invReq.getMtsEquipMasterCode() != null) {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterCode(invReq.getMtsEquipMasterCode());
			} else {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterId(invReq.getMtsEquipMasterId()).get();
			}
			equipment.setMtsLocationMasterId(invReq.getToLocationId());
			equipment.setModifiedDate(invReq.getCurrentDate());

			mtsEquipmentMasterRepository.saveAndFlush(equipment);

			result.put("message", "Inventory saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "inventory save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject equipmentLocation(EquiReq req) {
		JSONObject result = new JSONObject();
//		mtsEquipmentMasterRepository.fetchLocations();
		try {
			Map<String, Object> fromLocation = mtsEquipmentMasterRepository.fetchFromLocation(req.getMtsEquipMasterId());
			List<Map<String, Object>> toLocation = mtsEquipmentMasterRepository.fetchToLocation(req.getMtsEquipMasterId());
			
//			List<Map<String, Object>> data = mtsEquipmentMasterRepository.fetchLocations(req.getMtsEquipMasterId());
			JSONObject data = new JSONObject();
			data.put("fromLocation", fromLocation);
			data.put("toLocation", JsonUtil.toJsonArrayOfObjects(toLocation));
			
			result.put("data", data);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "inventory location fetch error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAvailableLocations() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsLocationMasterRepository.getAvailableLocations();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
			result.put("status", 1);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject equipmentFromLocationOfChallans(InvReq req) {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsEquipmentMasterRepository.equipmentFromLocationOfChallans(req.getMtsLocationMasterId());
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
