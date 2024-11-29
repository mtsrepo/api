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
	private String addressLine1;
	private String addressLine2;
	@JsonProperty("GSTN")
	private String GSTN;
	private String emailAddress;
	private String contactNumber;
	//new
		private String contactPerson;
		private String designation;
		private String department;
		private String pincode;
	
	
	//for update
	private Long mtsPartyAddressId;
}
