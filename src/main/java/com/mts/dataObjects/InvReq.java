package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InvReq {
	private String userId;
	private String authToken;
	private Long mtsLocationMasterId;
}
