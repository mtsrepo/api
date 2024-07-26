package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SavePartyReq;
import com.mts.entity.Company;
import com.mts.entity.MtsPartyMaster;
import com.mts.repository.CompanyRepository;
import com.mts.repository.MtsPartyMasterRepository;
import com.mts.service.PartyService;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	MtsPartyMasterRepository mtsPartyMasterRepository;

	@Override
	public JSONObject saveParty(SavePartyReq partyReq) {
		JSONObject result = new JSONObject();

		try {
			Company company = new Company();
			company.setName(partyReq.getName());
			company.setRegAddress(partyReq.getRegAddress());

			Company savedCompany = companyRepository.saveAndFlush(company);

			MtsPartyMaster party = new MtsPartyMaster();
			party.setCompanyId(savedCompany.getCompanyId());
			party.setRegNo(partyReq.getRegNo());
			party.setDetails(partyReq.getDetails());
			party.setGSTN(partyReq.getGSTN());
			party.setPartyName(partyReq.getName());

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
			List<MtsPartyMaster> data = mtsPartyMasterRepository.getAllParties();
			result.put("data", data);
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
