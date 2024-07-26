package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mts.dataObjects.SavePartAddReq;

public interface PartyAddressService {
	JSONObject savePartyAddress(SavePartAddReq partAddReq);

	JSONObject getAllPartyAddresses();

	List<Map<String, Object>> getPartyMasterIdAddress(Long mtsPartyMasterId);

}
