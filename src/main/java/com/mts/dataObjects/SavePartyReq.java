package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SavePartyReq {
	private String userId;
	private String authToken;
	private String regNo;
	private String details;
	private String partyName; 
	private String regAddress; 

	// for update
	private Long mtsPartyMasterId;
}
