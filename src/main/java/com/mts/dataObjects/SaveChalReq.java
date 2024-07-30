package com.mts.dataObjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties
@Data
public class SaveChalReq {
	private String userId;
	private String authToken;

	private List<GoodsDto> goodsForChallan;

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
