package com.mts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SavePartAddReq;
import com.mts.entity.MtsPartyAddress;
import com.mts.repository.MtsPartyAddressRepository;
import com.mts.service.PartyAddressService;
import com.mts.util.JsonUtil;

@Service
public class PartyAddressServiceImpl implements PartyAddressService {

	@Autowired
	MtsPartyAddressRepository mtsPartyAddressRepository;

	@Override
	public JSONObject savePartyAddress(SavePartAddReq partAddReq) {
		JSONObject result = new JSONObject();
		try {
			MtsPartyAddress partyAddress = new MtsPartyAddress();
			Random random = new Random();
			int fiveDigitNumber = 10000 + random.nextInt(90000);
			String code = "MADD" + fiveDigitNumber;

			partyAddress.setAddressCode(code);
			partyAddress.setMtsPartyMasterId(partAddReq.getMtsPartyMasterId());
			partyAddress.setCompanyId(partAddReq.getCompanyId());
			partyAddress.setDetails(partAddReq.getDetails());
			partyAddress.setName(partAddReq.getName());

			mtsPartyAddressRepository.saveAndFlush(partyAddress);

			result.put("message", "Party Address saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", "party address save error");
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAllPartyAddresses() {
		JSONObject result = new JSONObject();
		try {
			List<MtsPartyAddress> data = mtsPartyAddressRepository.getAllPartyAddresses();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartyMasterIdAddress(Long mtsPartyMasterId) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsPartyAddressRepository.getAddressByPartyId(mtsPartyMasterId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
