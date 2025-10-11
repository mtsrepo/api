package com.mts.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.EquiReq;
import com.mts.dataObjects.InvReq;
import com.mts.dataObjects.SaveInvReq;
import com.mts.entity.MtsChallanDocument;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsInventoryTransaction;
import com.mts.entity.MtsLocationMaster;
import com.mts.entity.MtsStatusMaster;
import com.mts.repository.MtsChallanDocumentRepository;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsInventoryTransactionRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.repository.MtsStatusMasterRepository;
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
	@Autowired
	MtsStatusMasterRepository mtsStatusMasterRepository;
	@Autowired
	MtsChallanDocumentRepository mtsChallanDocumentRepository;

	@Override
	public JSONObject saveInventory(SaveInvReq invReq) {
		JSONObject result = new JSONObject();
		MtsEquipmentMaster equipment = null;
		MtsInventoryTransaction inventoryTransaction = null;
		MtsChallanDocument challan = null;
		
		try {
			MtsLocationMaster toLocation = mtsLocationMasterRepository.findByMtsLocationMasterId(invReq.getMtsLocationMasterId());
			
			if (invReq.getMtsEquipMasterCode() != null) {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterCode(invReq.getMtsEquipMasterCode());
			} else {
				equipment = mtsEquipmentMasterRepository.findByMtsEquipMasterId(invReq.getMtsEquipMasterId()).get();
			}
			
			Optional<MtsChallanDocument> existingChallan = mtsChallanDocumentRepository
					.findByMtsChallanId(invReq.getMtsChallanId());
			
			if (existingChallan.isPresent()) {
				challan = existingChallan.get();
			}
			
			if(invReq.getInventoryTransactionId() != null) {
				inventoryTransaction = mtsInventoryTransactionRepository.findByInventoryTransactionIdAndIsActive(invReq.getInventoryTransactionId(), 1);
				
				if(inventoryTransaction != null) {
					
				}
				
			}else {
				inventoryTransaction = new MtsInventoryTransaction();
				inventoryTransaction.setMtsEquipMasterId(equipment.getMtsEquipMasterId());
				
				inventoryTransaction.setCreatedBy(Long.valueOf(invReq.getUserId()));
				inventoryTransaction.setCreatedOn(invReq.getCurrentDate());
			}
			
			if(invReq.getMtsChallanEquipId() != null) {
				inventoryTransaction.setMtsChallanEquipId(invReq.getMtsChallanEquipId());
			}
			
			inventoryTransaction.setFromLocationId(invReq.getFromLocationId());
			inventoryTransaction.setToLocationId(invReq.getToLocationId());
			
			if(challan!=null && (challan.getDespToLocationMasterId() == invReq.getToLocationId() && invReq.getToLocationId() == invReq.getMtsLocationMasterId())){
				inventoryTransaction.setIsActive(1);
				inventoryTransaction.setInTransitOrComplete(0);
			}else {
				inventoryTransaction.setIsActive(1);
			}
					
			
			if(invReq.getMtsLocationMasterId() == invReq.getFromLocationId()) {
				inventoryTransaction.setInTransitOrComplete(1);
				equipment.setCurrentStatus(2);
			}else if(invReq.getMtsLocationMasterId() == invReq.getToLocationId() && invReq.getMtsLocationMasterId() == 41L) {
				equipment.setCurrentStatus(1);
				equipment.setMtsLocationMasterId(41L);
			}else {
				equipment.setMtsLocationMasterId(invReq.getToLocationId());
				equipment.setCurrentStatus(mtsLocationMasterRepository.findByMtsLocationMasterId(invReq.getToLocationId()).getType());
			}
//			equipment.setMtsLocationMasterId(invReq.getToLocationId());

			mtsInventoryTransactionRepository.saveAndFlush(inventoryTransaction);

			equipment.setModifiedDate(invReq.getCurrentDate());
			equipment.setIsActive(1);

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
			List<Map<String, Object>> toLocation = mtsEquipmentMasterRepository.fetchToLocation(req.getMtsLocationMasterId(), req.getMtsEquipMasterId());
			
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

	@Override
	public JSONObject equipmentFromLocationAndStatus(InvReq req) {
		JSONObject result = new JSONObject();
		try {
			MtsLocationMaster location = mtsLocationMasterRepository.findByMtsLocationMasterId(req.getMtsLocationMasterId());
			MtsStatusMaster status = mtsStatusMasterRepository.findByStatusId(location.getType());
			List<Map<String, Object>> data = mtsEquipmentMasterRepository.equipmentFromLocationAndStatus(location.getMtsLocationMasterId(),status.getStatusId());
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
