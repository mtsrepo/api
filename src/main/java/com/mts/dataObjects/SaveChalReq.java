package com.mts.dataObjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SaveChalReq {
	private String userId;
	private String authToken;
	// for update
	private Long mtsChallanId;

	private List<GoodsDto> goodsForChallan;

	private Long companyId;
	private String deliveryAddr;
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
	private String txnType;
	
	private String customerRefNo;
	private Long challanDate;
}
