package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.GetChalReq;
import com.mts.dataObjects.GoodsDto;
import com.mts.dataObjects.SaveChalReq;
import com.mts.entity.MtsChallanDocument;
import com.mts.entity.MtsChallanEquipDtl;
import com.mts.entity.MtsEquipmentAvailability;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsLocationMaster;
import com.mts.repository.MtsChallanDocumentRepository;
import com.mts.repository.MtsChallanEquipDtlRepository;
import com.mts.repository.MtsEquipmentAvailabilityRepository;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.service.ChallanService;
import com.mts.util.JsonUtil;

@Service
public class ChallanServiceImpl implements ChallanService {

	@Autowired
	MtsChallanDocumentRepository mtsChallanDocumentRepository;
	@Autowired
	MtsChallanEquipDtlRepository mtsChallanEquipDtlRepository;
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsEquipmentAvailabilityRepository mtsEquipmentAvailabilityRepository;
	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;

	@Override
	public JSONObject saveChallan(SaveChalReq chalReq) {
		JSONObject result = new JSONObject();
		MtsChallanDocument challan = null;
		MtsChallanEquipDtl challanEquip = null;
//		MtsEquipmentAvailability equipAvail = null;
		MtsLocationMaster getDespFromLocationId = null;
		MtsLocationMaster getDespToLocationId =	null;	
		try {
			if (chalReq.getMtsChallanId() != null) {
				Optional<MtsChallanDocument> existingChallan = mtsChallanDocumentRepository
						.findByMtsChallanId(chalReq.getMtsChallanId());
				if (existingChallan.isPresent()) {
					challan = existingChallan.get();
					if(challan.getDespToLocationMasterId() == chalReq.getDespFrmLocationMasterId()) {
						challan.setCompletionStatus(1);
					}
				}
				
				getDespFromLocationId = mtsLocationMasterRepository.findByMtsLocationMasterId(chalReq.getDespFrmLocationMasterId()); 
				getDespToLocationId = mtsLocationMasterRepository.findByMtsLocationMasterId(chalReq.getDespToLocationMasterId());
			} else {
				challan = new MtsChallanDocument();
				Random random = new Random();
				int fiveDigitNumber = 10000 + random.nextInt(90000);
				String code = "MCHAL" + fiveDigitNumber;

				challan.setMtsChallanCode(code);
				challan.setCreateDate(new Date().getTime());
				challan.setCompletionStatus(0);
				
				getDespFromLocationId = mtsLocationMasterRepository.findByMtsPartyAddressId(chalReq.getDespFrmLocationMasterId()); 
//				System.out.println("chalReq.getDespToLocationMasterId() "+ chalReq.getDespToLocationMasterId());
				getDespToLocationId = mtsLocationMasterRepository.findByMtsPartyAddressId(chalReq.getDespToLocationMasterId());
			}
			
			
			
			challan.setTxnType(chalReq.getTxnType());
			challan.setType(String.valueOf(chalReq.getTxnType()));
			challan.setDeliveryAddress(chalReq.getDeliveryAddr());
			challan.setCompanyId(chalReq.getCompanyId());
			challan.setDespFrmLocationMasterId(getDespFromLocationId.getMtsLocationMasterId());
			challan.setDespToLocationMasterId(getDespToLocationId.getMtsLocationMasterId());
			challan.setConsignorId(chalReq.getConsignorId());
			challan.setConsigneeId(chalReq.getConsigneeId());
			challan.setChallanName(chalReq.getChallanName());
			challan.setChalanTNC(chalReq.getChalanTNC());
			challan.setTransporterName(chalReq.getTransporterName());
			challan.setTransporterType(chalReq.getTransporterType());
			challan.setVehicleNo(chalReq.getVehicleNo());
			challan.setTransporterLRNO(chalReq.getTransporterLRNO());
			challan.setDescription(chalReq.getDescription());
			challan.setCustomerRefNo(chalReq.getCustomerRefNo());
			challan.setChallanDate(chalReq.getChallanDate());
			challan.setIsActive(1);

			challan.setModifiedDate(new Date().getTime());

			MtsChallanDocument savedChallan = mtsChallanDocumentRepository.saveAndFlush(challan);

			List<MtsChallanEquipDtl> challanEquipList = new ArrayList<>();
			List<MtsEquipmentAvailability> equipAvailList = new ArrayList<>();

			for (GoodsDto val : chalReq.getGoodsForChallan()) {
				if (val.getMtsChallanEquipId() != null) {
					Optional<MtsChallanEquipDtl> existingChallanEquip = mtsChallanEquipDtlRepository
							.findByMtsChallanEquipId(val.getMtsChallanEquipId());
					if (existingChallanEquip.isPresent()) {
						challanEquip = existingChallanEquip.get();
					}
				} else {
					challanEquip = new MtsChallanEquipDtl();
					challanEquip.setCreateDate(new Date().getTime());
					challanEquip.setCompanyId(chalReq.getCompanyId());
					challanEquip.setMtsChallanId(savedChallan.getMtsChallanId());
				}
//				challanEquip.setType(val.getType());		i have to modify later
//				mtsEquipName
				challanEquip.setMtsEquipMasterId(val.getMtsEquipMasterId());

				MtsEquipmentMaster data = mtsEquipmentMasterRepository
						.findByMtsEquipMasterId(val.getMtsEquipMasterId()).get();
				
				MtsEquipmentAvailability equipQty = mtsEquipmentAvailabilityRepository.findByMtsEquipMasterId(data.getMtsEquipMasterId());

				challanEquip.setEquipName(data.getMtsEquipName());
				challanEquip.setQty(val.getQty());
				challanEquip.setValueOfGoods(val.getValueOfGoods());
				challanEquip.setTaxableValue(val.getTaxableValue());
				challanEquip.setIGSTPercentage(val.getIGSTPercentage());
				challanEquip.setIsActive(1);
				challanEquip.setModifiedDate(new Date().getTime());
				
				if(equipQty.getAvailable() >= val.getQty()) {
					equipQty.setInUse(equipQty.getInUse()+val.getQty());
					equipQty.setAvailable(equipQty.getAvailable() - val.getQty());
					equipQty.setModifiedOn(new Date().getTime());
				}else {
					result.put("message","challan quantity is over the available quanty for "+data.getSerialNo());
					result.put("status", 0);
				}

				challanEquipList.add(challanEquip);
				equipAvailList.add(equipQty);
			}

			mtsChallanEquipDtlRepository.saveAllAndFlush(challanEquipList); // i need to check if saveupdate both
																			// happening
			mtsEquipmentAvailabilityRepository.saveAllAndFlush(equipAvailList);

			result.put("message", "Challan saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "challan save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAllChallans(int take, int skip) {
		JSONObject result = new JSONObject();
		try {
//			List<Map<String, Object>> data = mtsChallanDocumentRepository.getAllChallans(skip, take);
//			JSONObject data = new JSONObject();
			int page = skip / take;
	        Pageable pageable = PageRequest.of(page, take);
			Page<MtsChallanDocument> resultPage = mtsChallanDocumentRepository.findAll(pageable);
			List<MtsChallanDocument> data = resultPage.getContent();

			for (MtsChallanDocument challan : data) {
				List<MtsChallanEquipDtl> goodsForChallan = mtsChallanEquipDtlRepository.findByMtsChallanId(challan.getMtsChallanId());
				challan.setGoodsForChallan(goodsForChallan);
			}
					
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject saveRevChallan(SaveChalReq chalReq) {
		JSONObject result = new JSONObject();
		MtsChallanDocument challan = null;
		try {
			if (chalReq.getMtsChallanId() != null) {
				Optional<MtsChallanDocument> existingChallan = mtsChallanDocumentRepository
						.findByMtsChallanId(chalReq.getMtsChallanId());
				if (existingChallan.isPresent()) {
					challan = existingChallan.get();

					MtsChallanDocument revChallan = new MtsChallanDocument();

					BeanUtils.copyProperties(challan, revChallan);

					Long temp = revChallan.getConsigneeId();
					revChallan.setConsigneeId(revChallan.getConsignorId());
					revChallan.setConsignorId(temp);

					mtsChallanDocumentRepository.saveAndFlush(revChallan);

					result.put("message", "Reverse Challan saved successfully");
					result.put("status", 1);
				}
			}
		} catch (Exception e) {
			result.put("message", "Reverse Challan save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getChallanDetails(GetChalReq getchal) {
		JSONObject result = new JSONObject();
		Map<String, Object> data = new HashMap<>();
//		MtsChallanDocument challan = null;
		try {
//			Optional<MtsChallanDocument> existingChallan = mtsChallanDocumentRepository.findByMtsChallanId(getchal.getMtsChallanId());
//			if (existingChallan.isPresent()) {
//				challan = existingChallan.get();
//			}
//			List<MtsChallanEquipDtl> goodsForChallan = mtsChallanEquipDtlRepository.findByMtsChallanId(getchal.getMtsChallanId());
			List<Map<String,Object>> goodsForChallan = mtsChallanEquipDtlRepository.getGoodsOfChallan(getchal.getMtsChallanId());
			
			//challan.setGoodsForChallan(goodsForChallan);
			
//			result.put("data", challan);
			data =  mtsChallanEquipDtlRepository.getChallanDetails(getchal.getMtsChallanId());
			result.put("data", data);
			result.put("goods", JsonUtil.toJsonArrayOfObjects(goodsForChallan));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject challanDashboard(int take, int skip) {
		JSONObject result = new JSONObject();
		try {
			List<Map<String,Object>> data = mtsChallanDocumentRepository.getChallanDash(take, skip);
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getTypeWiseGoodsData() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String,Object>> data = mtsEquipmentMasterRepository.getTypeWiseGoodsData();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject deleteGoodsData(Long mtsChallanEquipId) {
		JSONObject result = new JSONObject();
		MtsChallanEquipDtl challanEquip = null;
		try {
			Optional<MtsChallanEquipDtl> existingChallanEquip = mtsChallanEquipDtlRepository
					.findByMtsChallanEquipId(mtsChallanEquipId);
			
			if (existingChallanEquip.isPresent()) {
				challanEquip = existingChallanEquip.get();
			}
			
			challanEquip.setIsActive(0);
			
			mtsChallanEquipDtlRepository.saveAndFlush(challanEquip);
			
			result.put("message", "Goods deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
