package com.mts.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mts.dataObjects.SavePartyReq;

public interface PartyService {

	JSONObject saveParty(SavePartyReq partReq);

	JSONObject getAllParties();

	List<Map<String, Object>> getPartyMasterIdName(int isPartyAddress);

	JSONObject savePartyDetails(SavePartyReq partReq);

	JSONObject getDetailsAllParties(int take, int skip);

}
