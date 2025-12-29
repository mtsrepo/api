package com.mts.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.DispatchReq;
import com.mts.dataObjects.EquiReq;
import com.mts.dataObjects.InvReq;
import com.mts.dataObjects.InventoryLocationReq;
import com.mts.dataObjects.ReceiveReq;
import com.mts.dataObjects.SaveInvReq;
import com.mts.entity.MtsEquipmentAvailability;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsInventoryTransaction;
import com.mts.entity.MtsLocationMaster;
import com.mts.entity.MtsStatusMaster;
import com.mts.repository.MtsChallanDocumentRepository;
import com.mts.repository.MtsEquipmentAvailabilityRepository;
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
	@Autowired
	MtsEquipmentAvailabilityRepository mtsEquipmentAvailabilityRepository;

	@Transactional
	@Override
	public JSONObject saveInventory(SaveInvReq req) {

	    JSONObject result = new JSONObject();

	    try {
	        // 1. Load equipment
	        MtsEquipmentMaster equipment = mtsEquipmentMasterRepository
	                .findByMtsEquipMasterId(req.getMtsEquipMasterId())
	                .orElseThrow(() -> new RuntimeException("Equipment not found"));

	        // 2. Load locations
	        MtsLocationMaster fromLocation =
	                mtsLocationMasterRepository.findByMtsLocationMasterId(req.getFromLocationId());
	        MtsLocationMaster toLocation =
	                mtsLocationMasterRepository.findByMtsLocationMasterId(req.getToLocationId());

	        if (fromLocation == null || toLocation == null) {
	            throw new RuntimeException("Invalid location");
	        }

	        // 3. Find OPEN inventory transaction (if any)
	        MtsInventoryTransaction openTxn =
	                mtsInventoryTransactionRepository.findOpenTransaction(equipment.getMtsEquipMasterId());

	        // 4. DISPATCH FLOW
	        if ("DISPATCH".equalsIgnoreCase(req.getTransactionType())) {

	            if (openTxn != null) {
	                throw new RuntimeException("Equipment already in transit");
	            }

	            // Create DISPATCH transaction
	            MtsInventoryTransaction dispatchTxn = new MtsInventoryTransaction();
	            dispatchTxn.setMtsEquipMasterId(equipment.getMtsEquipMasterId());
	            dispatchTxn.setMtsChallanEquipId(req.getMtsChallanEquipId());
	            dispatchTxn.setFromLocationId(req.getFromLocationId());
	            dispatchTxn.setToLocationId(req.getToLocationId());
	            dispatchTxn.setTransactionType("DISPATCH");
	            dispatchTxn.setInTransitOrComplete(1); // OPEN
	            dispatchTxn.setCreatedBy(Long.valueOf(req.getUserId()));
	            dispatchTxn.setCreatedOn(req.getCurrentDate());
	            dispatchTxn.setIsActive(1);

	            mtsInventoryTransactionRepository.saveAndFlush(dispatchTxn);

	            // Move equipment to IN_TRANSIT
	            MtsLocationMaster inTransitLocation =
	                    mtsLocationMasterRepository.findByType(2); // type = IN_TRANSIT

	            equipment.setMtsLocationMasterId(inTransitLocation.getMtsLocationMasterId());
	            equipment.setCurrentStatus(2); // IN_TRANSIT
	            equipment.setModifiedDate(req.getCurrentDate());

	            mtsEquipmentMasterRepository.saveAndFlush(equipment);

	            result.put("status", 1);
	            result.put("message", "Dispatch completed successfully");
	            return result;
	        }

	        // 5. RECEIVE FLOW
	        if ("RECEIVE".equalsIgnoreCase(req.getTransactionType())) {

	            if (openTxn == null) {
	                throw new RuntimeException("No open dispatch found for this equipment");
	            }

	            if (!openTxn.getToLocationId().equals(req.getToLocationId())) {
	                throw new RuntimeException("Receiving at wrong location");
	            }

	            // Close previous OPEN transaction
	            openTxn.setInTransitOrComplete(0); // CLOSED
	            openTxn.setModifiedOn(req.getCurrentDate());
	            mtsInventoryTransactionRepository.saveAndFlush(openTxn);

	            // Create RECEIVE transaction (history)
	            MtsInventoryTransaction receiveTxn = new MtsInventoryTransaction();
	            receiveTxn.setMtsEquipMasterId(equipment.getMtsEquipMasterId());
	            receiveTxn.setMtsChallanEquipId(req.getMtsChallanEquipId());
	            receiveTxn.setFromLocationId(openTxn.getFromLocationId());
	            receiveTxn.setToLocationId(req.getToLocationId());
	            receiveTxn.setTransactionType("RECEIVE");
	            receiveTxn.setInTransitOrComplete(0); // CLOSED
	            receiveTxn.setCreatedBy(Long.valueOf(req.getUserId()));
	            receiveTxn.setCreatedOn(req.getCurrentDate());
	            receiveTxn.setIsActive(1);

	            mtsInventoryTransactionRepository.saveAndFlush(receiveTxn);

	            // Update equipment snapshot
	            equipment.setMtsLocationMasterId(req.getToLocationId());
	            equipment.setCurrentStatus(toLocation.getType());
	            equipment.setModifiedDate(req.getCurrentDate());

	            mtsEquipmentMasterRepository.saveAndFlush(equipment);

	            result.put("status", 1);
	            result.put("message", "Receive completed successfully");
	            return result;
	        }

	        throw new RuntimeException("Invalid transaction type");

	    } catch (Exception e) {
	        e.printStackTrace();
	        result.put("status", 0);
	        result.put("message", e.getMessage());
	        return result;
	    }
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
			List<Map<String, Object>> data1 = mtsEquipmentMasterRepository.nonLocatedEquipmentsFromStatus();
			data.addAll(data1);
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

//==============================================\\
	
	/* =====================================================
    1. DISPATCHABLE EQUIPMENT LIST
    ===================================================== */
 @Override
 public JSONObject getDispatchableEquipment(InventoryLocationReq req) {

     JSONObject result = new JSONObject();
     try {
         List<Map<String, Object>> data = (req.getLocationId() == 999) ? mtsEquipmentMasterRepository.findExternalDispatchable()
            : mtsEquipmentMasterRepository.getDispatchableEquipment(req.getLocationId());

         result.put("data", JsonUtil.toJsonArrayOfObjects(data));
         result.put("status", 1);

     } catch (Exception e) {
         e.printStackTrace();
         result.put("status", 0);
         result.put("message", "Failed to fetch dispatchable equipment");
     }
     return result;
 }

 /* =====================================================
    2. DISPATCH EQUIPMENT (OUT)
    ===================================================== */
 @Override
 @Transactional
 public JSONObject dispatchEquipment(DispatchReq req) {

     JSONObject result = new JSONObject();

     try {
         MtsEquipmentMaster equipment =
             mtsEquipmentMasterRepository.findById(req.getMtsEquipMasterId())
             .orElseThrow(() -> new RuntimeException("Equipment not found"));

         // VALIDATION
         if (req.getFromLocationId() == 999 && req.getToLocationId() == 999) {
        	    throw new RuntimeException("Invalid movement: External to External");
        	}
         
         if (equipment.getMtsLocationMasterId() != null &&
        		    !equipment.getMtsLocationMasterId().equals(req.getFromLocationId())) {
        		    throw new RuntimeException("Equipment not at source location");
        		}

         if (equipment.getCurrentStatus() != null && equipment.getCurrentStatus() == 2) {
             throw new RuntimeException("Equipment already in transit");
         }
         
//         MtsEquipmentAvailability availability =
//                 mtsEquipmentAvailabilityRepository
//                     .findByMtsEquipMasterId(equipment.getMtsEquipMasterId());
//         
//         int qty = req.getQty(); // for asset = 1
//         
//         if (availability.getAvailable() < qty) {
//             throw new RuntimeException("Insufficient available quantity");
//         }

         // CREATE INVENTORY TRANSACTION (OPEN)
         MtsInventoryTransaction tx = new MtsInventoryTransaction();
         tx.setMtsEquipMasterId(equipment.getMtsEquipMasterId());
         tx.setFromLocationId(req.getFromLocationId());
         tx.setToLocationId(req.getToLocationId());
         tx.setMtsChallanEquipId(req.getMtsChallanEquipId());
         tx.setTransactionType("DISPATCH");
         tx.setCreatedBy(req.getUserId());
         tx.setCreatedOn(req.getDispatchDate());
         tx.setInTransitOrComplete(1); // OPEN
         tx.setIsActive(1);

         mtsInventoryTransactionRepository.saveAndFlush(tx);
         
         // UPDATE AVAILABILITY  ✅ no need
//         availability.setAvailable(availability.getAvailable() - qty);
//         availability.setInUse(availability.getInUse() + qty);
//         availability.setModifiedOn(req.getDispatchDate());

//         mtsEquipmentAvailabilityRepository.saveAndFlush(availability);

         // UPDATE EQUIPMENT → IN_TRANSIT
         equipment.setCurrentStatus(2); // IN_TRANSIT
         equipment.setMtsLocationMasterId(2L); // virtual location
         equipment.setModifiedDate(req.getDispatchDate());

         mtsEquipmentMasterRepository.saveAndFlush(equipment);

         result.put("status", 1);
         result.put("message", "Equipment dispatched successfully");

     } catch (Exception e) {
         e.printStackTrace();
         result.put("status", 0);
         result.put("message", e.getMessage());
     }
     return result;
 }

 /* =====================================================
    3. RECEIVABLE EQUIPMENT LIST
    ===================================================== */
 @Override
 public JSONObject getReceivableEquipment(InventoryLocationReq req) {

     JSONObject result = new JSONObject();
     try {
         List<Map<String, Object>> data =
             mtsEquipmentMasterRepository.getReceivableEquipment(req.getLocationId());

         result.put("data", JsonUtil.toJsonArrayOfObjects(data));
         result.put("status", 1);

     } catch (Exception e) {
         e.printStackTrace();
         result.put("status", 0);
         result.put("message", "Failed to fetch receivable equipment");
     }
     return result;
 }

 /* =====================================================
    4. RECEIVE EQUIPMENT (IN)
    ===================================================== */
 @Override
 @Transactional
 public JSONObject receiveEquipment(ReceiveReq req) {

     JSONObject result = new JSONObject();

     try {
    	 
    	 if (req.getReceiveLocationId() == 999) {
    		    throw new RuntimeException("Cannot receive into External location");
    		}

         // FETCH OPEN TRANSACTION
         MtsInventoryTransaction tx =
             mtsInventoryTransactionRepository.findById(req.getInventoryTransactionId())
             .orElseThrow(() -> new RuntimeException("Inventory transaction not found"));

         if (tx.getInTransitOrComplete() == 0) {
             throw new RuntimeException("Inventory transaction already completed");
         }
         
//         int qty = req.getQty(); // asset = 1

         // FETCH AVAILABILITY
         MtsEquipmentAvailability availability =
             mtsEquipmentAvailabilityRepository
                 .findByMtsEquipMasterId(req.getMtsEquipMasterId());

//         if (availability.getInUse() < qty) {
//             throw new RuntimeException("Invalid receive quantity");
//         }
         
//         availability.setModifiedOn(req.getReceiveDate());


         // CLOSE TRANSACTION
         tx.setInTransitOrComplete(0); // COMPLETE
         tx.setModifiedBy(req.getUserId());
         tx.setModifiedOn(req.getReceiveDate());

         mtsInventoryTransactionRepository.saveAndFlush(tx);
         
      // UPDATE AVAILABILITY ✅
//         availability.setAvailable(availability.getAvailable() + qty);
//         availability.setInUse(availability.getInUse() - qty);
         availability.setModifiedOn(req.getReceiveDate());

         mtsEquipmentAvailabilityRepository.saveAndFlush(availability);

         // UPDATE EQUIPMENT LOCATION
         MtsEquipmentMaster equipment =
             mtsEquipmentMasterRepository.findById(req.getMtsEquipMasterId())
             .orElseThrow(() -> new RuntimeException("Equipment not found"));

         MtsLocationMaster receiveLocation =
             mtsLocationMasterRepository.findById(req.getReceiveLocationId())
             .orElseThrow(() -> new RuntimeException("Location not found"));

         if (receiveLocation.getType() == 1) { // WAREHOUSE
			    availability.setAvailable(availability.getAvailable() + 1);
			}
			else if (receiveLocation.getType() == 3) { // PROJECT SITE
			    availability.setInUse(availability.getInUse() + 1);
			}
         
         equipment.setMtsLocationMasterId(req.getReceiveLocationId());
         equipment.setCurrentStatus(receiveLocation.getType());
         equipment.setModifiedDate(req.getReceiveDate());

         mtsEquipmentMasterRepository.saveAndFlush(equipment);

         result.put("status", 1);
         result.put("message", "Equipment received successfully");

     } catch (Exception e) {
         e.printStackTrace();
         result.put("status", 0);
         result.put("message", e.getMessage());
     }
     return result;
 }

}
