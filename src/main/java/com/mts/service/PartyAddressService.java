package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.SavePartAddReq;

public interface PartyAddressService {
	JSONObject savePartyAddress(SavePartAddReq partAddReq);

	JSONObject getAllPartyAddresses();
}
