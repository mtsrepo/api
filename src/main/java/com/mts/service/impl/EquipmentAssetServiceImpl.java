package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SaveAssetReq;
import com.mts.entity.MtsEquipmentAvailability;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsPartyMaster;
import com.mts.entity.MtsQrCode;
import com.mts.repository.MtsEquipmentAvailabilityRepository;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsEquipmentTypeMasterRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.repository.MtsQrCodeRepository;
import com.mts.service.EquipmentAssetService;
import com.mts.service.QrCodeService;
import com.mts.util.JsonUtil;

@Service
public class EquipmentAssetServiceImpl implements EquipmentAssetService {
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentTypeMasterRepository mtsEquipmentTypeMasterRepository;
	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;
	@Autowired
	MtsQrCodeRepository mtsQrCodeRepository;
	@Autowired
	QrCodeService qrCodeService;
	@Autowired
	MtsEquipmentAvailabilityRepository mtsEquipmentAvailabilityRepository;

	@Override
	public JSONObject getAllAssets() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsEquipmentMasterRepository.getAllAssets();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
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
	@Transactional
	public JSONObject saveAsset(SaveAssetReq asstReq) {
		JSONObject result = new JSONObject();
		Long userId = 0L;
		MtsEquipmentMaster asset = null;
		
		try {
			if (asstReq.getMtsEquipMasterId() != null) {
				Optional<MtsEquipmentMaster> existingAsset = mtsEquipmentMasterRepository
						.findByMtsEquipMasterId(asstReq.getMtsEquipMasterId());
				if (existingAsset.isPresent()) {
					asset = existingAsset.get();
				}
			} else {
				Optional<MtsEquipmentMaster> checkAlready = mtsEquipmentMasterRepository.findBySerialNo(asstReq.getSerialNo()); 
				checkAlready.ifPresent(existingParty -> {
				    throw new RuntimeException("Asset with this serial number already exists.");
				});
				
				asset = new MtsEquipmentMaster();
				Random random = new Random();
				int fiveDigitNumber = 10000 + random.nextInt(90000);

				String code = "MEQU" + fiveDigitNumber;
				userId = ((new Date().getTime() * 10) + (long) (Math.floor(Math.random() * 90L) + 100L));

				asset.setMtsEquipMasterId(userId);
				asset.setMtsEquipMasterCode(code);
				asset.setCreateDate(new Date().getTime());
				
				MtsQrCode qrCode = qrCodeService.generateAndSaveQRCode(String.valueOf(asset.getMtsEquipMasterId()));
				if (qrCode != null) {
					asset.setMtsQrId(qrCode.getMtsQrId());
				}
				
				MtsEquipmentAvailability availability = new MtsEquipmentAvailability();
				availability.setMtsEquipMasterId(asset.getMtsEquipMasterId());
				availability.setTotalNo(1);
				availability.setInUse(0);
				availability.setAvailable(0);
				availability.setCreatedOn(new Date().getTime());
				availability.setModifiedOn(new Date().getTime());
				availability.setIsActive(1);
				
				mtsEquipmentAvailabilityRepository.saveAndFlush(availability);
			}
	        
			asset.setSerialNo(asstReq.getSerialNo());
			asset.setMtsEquipTypeMasterId(asstReq.getMtsEquipTypeMasterId());
			asset.setMtsEquipName(asstReq.getAssetName());
			asset.setManufacturedCompany(asstReq.getManufacturedCompany());
			asset.setSuppliedCompany(asstReq.getSuppliedCompany());
			asset.setDescription(asstReq.getDescription());
			asset.setDateOfPurchase(asstReq.getDateOfPurchase());
			asset.setLastDateOfWarranty(asstReq.getLastDateOfWarranty());
//			asset.setMtsLocationMasterId(1L);		// at first not decided where it will
			asset.setModifiedDate(new Date().getTime());
			
			
			mtsEquipmentMasterRepository.saveAndFlush(asset);
			
			result.put("message", "Asset saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", e.getMessage());
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getLocationMasterIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsLocationMasterRepository.getLocationMasterIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
