package com.mts.dataObjects;

import lombok.Data;

@Data
public class SavePartAddReq {
	private String userId;
	private String authToken;
	private Long mtsPartyMasterId;
	private Long companyId;
	private String details;
	private String name;
}
