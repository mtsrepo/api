package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EquiReq {
	private String userId;
	private String authToken;
	private Long mtsEquipMasterId;
	private Long mtsChallanEquipId;
	private Integer inTransitOrComplete;
	private Long mtsLocationMasterId;
}
