package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.SavePartyReq;

public interface PartyService {

	JSONObject saveParty(SavePartyReq partReq);

	JSONObject getAllParties();

}
