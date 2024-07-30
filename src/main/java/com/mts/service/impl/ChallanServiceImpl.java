package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.GoodsDto;
import com.mts.dataObjects.SaveChalReq;
import com.mts.entity.MtsChallanDocument;
import com.mts.entity.MtsChallanEquipDtl;
import com.mts.repository.MtsChallanDocumentRepository;
import com.mts.repository.MtsChallanEquipDtlRepository;
import com.mts.service.ChallanService;

@Service
public class ChallanServiceImpl implements ChallanService {

	@Autowired
	MtsChallanDocumentRepository mtsChallanDocumentRepository;
	@Autowired
	MtsChallanEquipDtlRepository mtsChallanEquipDtlRepository;

	@Override
	public JSONObject saveChallan(SaveChalReq chalReq) {
		JSONObject result = new JSONObject();
		try {
			MtsChallanDocument challan = new MtsChallanDocument();
			Random random = new Random();
			int fiveDigitNumber = 10000 + random.nextInt(90000);
			String code = "MCHAL" + fiveDigitNumber;

			challan.setMtsChallanCode(code);
			challan.setCompanyId(chalReq.getCompanyId());
			challan.setDespFrmLocationMasterId(chalReq.getDespFrmLocationMasterId());
			challan.setDespToLocationMasterId(chalReq.getDespToLocationMasterId());
			challan.setConsignorId(chalReq.getConsignorId());
			challan.setConsigneeId(chalReq.getConsigneeId());
			challan.setChallanName(chalReq.getChallanName());
			challan.setChalanTNC(chalReq.getChalanTNC());
			challan.setTransporterName(chalReq.getTransporterName());
			challan.setTransporterType(chalReq.getTransporterType());
			challan.setVehicleNo(chalReq.getVehicleNo());
			challan.setTransporterLRNO(chalReq.getTransporterLRNO());
			challan.setDescription(chalReq.getDescription());
			challan.setIsActive(1);
			challan.setCreateDate(new Date().getTime());
			challan.setModifiedDate(new Date().getTime());

			MtsChallanDocument savedChallan = mtsChallanDocumentRepository.saveAndFlush(challan);

			List<MtsChallanEquipDtl> challanEquipList = new ArrayList<>();

			for (GoodsDto val : chalReq.getGoodsForChallan()) {
				MtsChallanEquipDtl challanEquip = new MtsChallanEquipDtl();
				challanEquip.setType(val.getType());
				challanEquip.setCompanyId(chalReq.getCompanyId());
				challanEquip.setMtsChallanId(savedChallan.getMtsChallanId());
				challanEquip.setMtsEquipMasterId(val.getMtsEquipMasterId());
				challanEquip.setQty(val.getQty());
				challanEquip.setValueOfGoods(val.getValueOfGoods());
				challanEquip.setTaxableValue(val.getTaxableValue());
				challanEquip.setIGSTPercentage(val.getIGSTPercentage());
				challanEquip.setIsActive(1);
				challanEquip.setCreateDate(new Date().getTime());
				challanEquip.setModifiedDate(new Date().getTime());

				challanEquipList.add(challanEquip);
			}

			mtsChallanEquipDtlRepository.saveAllAndFlush(challanEquipList);

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
					
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
