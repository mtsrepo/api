package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.EquiReq;
import com.mts.dataObjects.SaveInvReq;

public interface InventoryService {

	JSONObject saveInventory(SaveInvReq invReq);

	JSONObject equipmentLocation(EquiReq req);

}
