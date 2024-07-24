package com.mts.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public JSONObject saveParty(SaveChalReq chalReq) {
		JSONObject result = new JSONObject();
		try {
			MtsChallanDocument challan = new MtsChallanDocument();
			Random random = new Random();
			int fiveDigitNumber = 10000 + random.nextInt(90000);
			String code = "MCHAL" + fiveDigitNumber;

			challan.setMtsChallanCode(code);
			challan.setType(chalReq.getType());
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

			mtsChallanDocumentRepository.saveAndFlush(challan);

			MtsChallanEquipDtl challanEquip = new MtsChallanEquipDtl();
			challanEquip.setType(chalReq.getType());
			challanEquip.setCompanyId(chalReq.getCompanyId());

			challanEquip.setMtsEquipMasterId(chalReq.getMtsEquipMasterId());
			challanEquip.setQty(chalReq.getQty());
			challanEquip.setValueOfGoods(chalReq.getValueOfGoods());
			challanEquip.setTaxableValue(chalReq.getTaxableValue());
			challanEquip.setIGSTPercentage(chalReq.getIGSTPercentage());
			challanEquip.setIsActive(1);
			challanEquip.setCreateDate(new Date().getTime());
			challanEquip.setModifiedDate(new Date().getTime());

			mtsChallanEquipDtlRepository.saveAndFlush(challanEquip);

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
	public JSONObject getAllChallans() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsChallanDocumentRepository.getAllChallans();
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
