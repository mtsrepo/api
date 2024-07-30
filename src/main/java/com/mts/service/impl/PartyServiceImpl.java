package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SavePartyReq;
import com.mts.entity.MtsPartyMaster;
import com.mts.repository.CompanyRepository;
import com.mts.repository.MtsPartyMasterRepository;
import com.mts.service.PartyService;
import com.mts.util.JsonUtil;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	MtsPartyMasterRepository mtsPartyMasterRepository;

	private static final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);

	@Override
	public JSONObject saveParty(SavePartyReq partyReq) {
		JSONObject result = new JSONObject();

		try {
			MtsPartyMaster party = new MtsPartyMaster();
			party.setRegNo(partyReq.getRegNo());
			party.setDetails(partyReq.getDetails());
			party.setGSTN(partyReq.getGSTN());
			party.setPartyName(partyReq.getName());
			party.setEmailAddress(partyReq.getEmailAddress());
			party.setContactNumber(partyReq.getContactNumber());
			party.setCompanyId(1L); // company id set 1 hardcoded for owener company now

			mtsPartyMasterRepository.saveAndFlush(party);

			result.put("message", "Party saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "party save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAllParties() {
		JSONObject result = new JSONObject();
		try {
			List<MtsPartyMaster> data = mtsPartyMasterRepository.findAll();

			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartyMasterIdName() {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsPartyMasterRepository.getPartyMasterIdName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
