package com.mts.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SaveInvReq;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsInventoryTransaction;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsInventoryTransactionRepository;
import com.mts.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	MtsInventoryTransactionRepository mtsInventoryTransactionRepository;
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;

	@Override
	public JSONObject saveInventory(SaveInvReq invReq) {
		JSONObject result = new JSONObject();
		MtsEquipmentMaster equipment = null;
		try {
			MtsInventoryTransaction inventoryTransaction = new MtsInventoryTransaction();
			inventoryTransaction.setMtsEquipMasterId(invReq.getMtsEquipMasterId());
			inventoryTransaction.setMtsLocationMasterId(invReq.getMtsLocationMasterId());
			inventoryTransaction.setIsActive(1);
			inventoryTransaction.setCreatedBy(Long.valueOf(invReq.getUserId()));
			inventoryTransaction.setCreatedOn(invReq.getCurrentDate());

			mtsInventoryTransactionRepository.saveAndFlush(inventoryTransaction);

			if (invReq.getMtsEquipMasterCode() != null) {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterCode(invReq.getMtsEquipMasterCode());
			} else {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterId(invReq.getMtsEquipMasterId()).get();
			}
			equipment.setMtsLocationMasterId(invReq.getMtsLocationMasterId());
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

}
