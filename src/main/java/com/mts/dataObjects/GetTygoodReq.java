package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTygoodReq {
	private String userId;
	private String authToken;
	private Long mtsPartyAddressId;
}
