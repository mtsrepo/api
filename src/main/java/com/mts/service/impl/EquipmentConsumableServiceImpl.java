package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SaveConsumableReq;
import com.mts.entity.MtsEquipmentAvailability;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsQrCode;
import com.mts.repository.MtsEquipmentAvailabilityRepository;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.service.EquipmentConsumableService;
import com.mts.service.QrCodeService;
import com.mts.util.JsonUtil;

@Service
public class EquipmentConsumableServiceImpl implements EquipmentConsumableService {
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentTypeMasterRepository mtsEquipmentTypeMasterRepository;
	@Autowired
	QrCodeService qrCodeService;
	@Autowired
	MtsEquipmentAvailabilityRepository mtsEquipmentAvailabilityRepository; 
	
	@Override
	public List<Map<String, Object>> getConsumableTypeIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsEquipmentTypeMasterRepository.getConsumableTypeIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public JSONObject getAllConsumables() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsEquipmentMasterRepository.getAllConsumables();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject saveConsumable(SaveConsumableReq consReq) {
		JSONObject result = new JSONObject();
		Long userId = 0L;
		MtsEquipmentMaster consumable = null;
		MtsEquipmentAvailability availability = null;
		try {
			if (consReq.getMtsEquipMasterId() != null) {
				Optional<MtsEquipmentMaster> existingAsset = mtsEquipmentMasterRepository
						.findByMtsEquipMasterId(consReq.getMtsEquipMasterId());
				if (existingAsset.isPresent()) {
					consumable = existingAsset.get();
				}
				
				MtsEquipmentAvailability exist = mtsEquipmentAvailabilityRepository
						.findByMtsEquipMasterId(consReq.getMtsEquipMasterId());
				if(exist != null) {
					availability = exist;
				}
			} else {
				consumable = new MtsEquipmentMaster();
				Random random = new Random();
				int fiveDigitNumber = 10000 + random.nextInt(90000);

				String code = "MEQU" + fiveDigitNumber;
				userId = ((new Date().getTime() * 10) + (long) (Math.floor(Math.random() * 90L) + 100L));

				consumable.setMtsEquipMasterId(userId);
				consumable.setMtsEquipMasterCode(code);
				consumable.setCreateDate(new Date().getTime());

				MtsQrCode qrCode = qrCodeService
						.generateAndSaveQRCode(String.valueOf(consumable.getMtsEquipMasterId()));
				if (qrCode != null) {
					consumable.setMtsQrId(qrCode.getMtsQrId());
				}
				
				availability = new MtsEquipmentAvailability();
				availability.setMtsEquipMasterId(consumable.getMtsEquipMasterId());
				availability.setTotalNo(consReq.getQuantity());
				availability.setInUse(0);
				availability.setAvailable(consReq.getQuantity());
				availability.setCreatedOn(new Date().getTime());
				
			}

			consumable.setSerialNo(consReq.getSerialNo());
			consumable.setMtsEquipTypeMasterId(consReq.getMtsEquipTypeMasterId());
			consumable.setMtsEquipName(consReq.getConsumableName());
			consumable.setManufacturedCompany(consReq.getManufacturedCompany());
			consumable.setSuppliedCompany(consReq.getSuppliedCompany());
			consumable.setDescription(consReq.getDescription());
			consumable.setDateOfPurchase(consReq.getDateOfPurchase());
			consumable.setLastDateOfWarranty(consReq.getLastDateOfWarranty());
//			consumable.setMtsLocationMasterId(1L);			// at first not decided where it will
			consumable.setModifiedDate(new Date().getTime());
			
			mtsEquipmentMasterRepository.saveAndFlush(consumable);
			
			availability.setModifiedOn(new Date().getTime());
			availability.setIsActive(1);
			availability.setTotalNo(consReq.getQuantity());
			availability.setAvailable(consReq.getQuantity()-availability.getInUse());
			mtsEquipmentAvailabilityRepository.saveAndFlush(availability);
			
			result.put("message", "Consumable saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "consumable save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
}
