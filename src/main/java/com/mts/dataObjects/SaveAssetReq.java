package com.mts.dataObjects;

import lombok.Data;

@Data
public class SaveAssetReq {
	private String userId;
	private String authToken;
	// for update
	private Long mtsEquipMasterId;

	private Long mtsEquipTypeMasterId;
	private String serialNo;
	private String assetName;
	private String manufacturedCompany;
	private String suppliedCompany;
	private String description;
	private Long dateOfPurchase;
	private Long lastDateOfWarranty;
	private Long mtsLocationMasterId;
}
