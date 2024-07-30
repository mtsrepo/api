package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.SaveChalReq;

public interface ChallanService {

	JSONObject saveChallan(SaveChalReq chalReq);

	JSONObject getAllChallans(int take, int skip);

}
