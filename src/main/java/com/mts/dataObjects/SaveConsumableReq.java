package com.mts.dataObjects;

import lombok.Data;

@Data
public class SaveConsumableReq {
	private String userId;
	private String authToken;
	private Long mtsEquipTypeMasterId;
	private String consumableName;
	private String manufacturedCompany;
	private String suppliedCompany;
	private String description;
	private Long dateOfPurchase;
	private Long lastDateOfWarranty;
}
