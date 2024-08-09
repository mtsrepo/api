package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SavePartAddReq {
	private String userId;
	private String authToken;
	private Long mtsPartyMasterId;
	private Long companyId;
	private String details;
	private String name;
	//for update
	private Long mtsPartyAddressId;
}
