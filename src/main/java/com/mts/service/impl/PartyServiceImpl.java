package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SavePartyReq;
import com.mts.entity.MtsChallanDocument;
import com.mts.entity.MtsPartyDetails;
import com.mts.entity.MtsPartyMaster;
import com.mts.repository.CompanyRepository;
import com.mts.repository.MtsPartyDetailsRepository;
import com.mts.repository.MtsPartyMasterRepository;
import com.mts.service.PartyService;
import com.mts.util.JsonUtil;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	MtsPartyMasterRepository mtsPartyMasterRepository;
	@Autowired
	MtsPartyDetailsRepository mtsPartyDetailsRepository;

	private static final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);

	@Override
	public JSONObject saveParty(SavePartyReq partyReq) {
		JSONObject result = new JSONObject();
		MtsPartyMaster party = null;
		try {
			if (partyReq.getMtsPartyMasterId() != null) {
				Optional<MtsPartyMaster> existingParty = mtsPartyMasterRepository
						.findByMtsPartyMasterId(partyReq.getMtsPartyMasterId());
				if (existingParty.isPresent()) {
					party = existingParty.get();
				}
			}else {
				Optional<MtsPartyMaster> checkAlready = mtsPartyMasterRepository.findByRegNo(partyReq.getRegNo()); 
				checkAlready.ifPresent(existingParty -> {
				    throw new RuntimeException("Party with this registration number already exists.");
				});
				
				party = new MtsPartyMaster();
				/*
				 * made a party code if needed later
				 * 
				 * Random random = new Random(); int fiveDigitNumber = 10000 +
				 * random.nextInt(90000); String code = "MPAR" + fiveDigitNumber;
				 * 
				 * party.setPartyCode(code);
				 */
				party.setCreatedOn(new Date().getTime());
			}

			party.setRegNo(partyReq.getRegNo());
			party.setDetails(partyReq.getDetails());
			party.setPartyName(partyReq.getPartyName());
//			party.setGSTN(partyReq.getGSTN());
//			party.setEmailAddress(partyReq.getEmailAddress());
//			party.setContactNumber(partyReq.getContactNumber());
			party.setCompanyId(1L); // company id set 1 hardcoded for owener company now
			party.setRegisteredAddress(partyReq.getRegAddress());
			party.setModifiedOn(new Date().getTime());

			mtsPartyMasterRepository.saveAndFlush(party);

			result.put("message", "Party saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", e.getMessage());
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
	public List<Map<String, Object>> getPartyMasterIdName(int isPartyAddress) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			if(isPartyAddress == 0) {
				result = mtsPartyMasterRepository.getPartyMasterIdNameForChallan();
			}else {
				result = mtsPartyMasterRepository.getPartyMasterIdNameForAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject savePartyDetails(SavePartyReq partReq) {
		JSONObject result = new JSONObject();
		MtsPartyDetails party = new MtsPartyDetails();
		try {
	        BeanUtils.copyProperties(partReq, party);

	        mtsPartyDetailsRepository.saveAndFlush(party);
	        
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
	public JSONObject getDetailsAllParties(int take, int skip) {
		JSONObject result = new JSONObject();
		try {
			int page = skip / take;
	        Pageable pageable = PageRequest.of(page, take);
			Page<MtsPartyDetails> resultPage = mtsPartyDetailsRepository.findAll(pageable);
			List<MtsPartyDetails> data = resultPage.getContent();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
