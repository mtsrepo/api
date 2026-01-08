package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveConsumableReq {
	private String userId;
	private String authToken;
	// for update
	private Long mtsEquipMasterId;
	private Integer quantity;
	private String serialNo;
	private Long mtsEquipTypeMasterId;
	private String consumableName;
	private String manufacturedCompany;
	private String suppliedCompany;
	private String description;
	private Long dateOfPurchase;
	private Long lastDateOfWarranty;
	private Long mtsLocationMasterId;
	
//	private Long mtsPartyMasterId;
//	private Long mtsPartyAddressId;
}
