package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SaveInvReq {
	private String userId;
	private String authToken;
	private Long mtsEquipMasterId;
	private Long fromLocationId;
	private Long toLocationId;
	private Long currentDate;
	private String mtsEquipMasterCode;
	
	private Long mtsLocationMasterId;
	private Long mtsChallanEquipId;
//	private Integer inTransitOrComplete;
	private Long inventoryTransactionId;
	private Long mtsChallanId;
}
