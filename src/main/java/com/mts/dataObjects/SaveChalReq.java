package com.mts.dataObjects;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties
@Data
public class SaveChalReq {
	private String userId;
	private String authToken;

	private Long mtsEquipMasterId;
	private String type;
	private Integer qty;
	private BigDecimal valueOfGoods;
	private BigDecimal taxableValue;
	private BigDecimal iGSTPercentage;

	private Long companyId;
	private Long despFrmLocationMasterId;
	private Long despToLocationMasterId;
	private Long consignorId;
	private Long consigneeId;
	private String challanName;
	private String chalanTNC;
	private String transporterName;
	private String transporterType;
	private String vehicleNo;
	private String transporterLRNO;
	private String description;
}
