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
//	@JsonProperty("GSTN")
//	private String GSTN;
	private String name; // same as company table name
	private String regAddress; // for company table
	// account -- need to ask
//	private String emailAddress;
//	private String contactNumber;

	// for update
	private Long mtsPartyMasterId;
}
