package com.mts.service;

import java.util.Map;

import org.json.JSONObject;

import com.mts.dataObjects.GetChalReq;
import com.mts.dataObjects.SaveChalReq;

public interface ChallanService {

	JSONObject saveChallan(SaveChalReq chalReq);

	JSONObject getAllChallans(int take, int skip);

	JSONObject saveRevChallan(SaveChalReq chalReq);

	JSONObject getChallanDetails(GetChalReq getchal);

	JSONObject challanDashboard(int take, int skip);

	JSONObject getTypeWiseGoodsData(Long mtsLocationMasterId);

	JSONObject deleteGoodsData(Long mtsChallanEquipId);

}
