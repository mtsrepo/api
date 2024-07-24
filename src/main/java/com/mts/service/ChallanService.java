package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.SaveChalReq;

public interface ChallanService {

	JSONObject saveParty(SaveChalReq chalReq);

	JSONObject getAllChallans();

}
