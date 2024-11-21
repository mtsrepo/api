package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SavePartAddReq {
	private String userId;
	private String authToken;
	
	private Long mtsPartyMasterId;
	private Long companyId;
	private String branchDetails;
	private String partyAddress;
	@JsonProperty("GSTN")
	private String GSTN;
	private String emailAddress;
	private String contactNumber;
	
	//for update
	private Long mtsPartyAddressId;
}
