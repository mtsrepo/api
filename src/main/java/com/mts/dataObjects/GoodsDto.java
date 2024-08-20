package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//mts_challan_equip_dtl attributes
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoodsDto {
	// for update
	private Long mtsChallanEquipId;
	private Long mtsEquipMasterId;
	private String type;
	private Integer qty;
	private Double valueOfGoods;
	private Double taxableValue;
	private Double iGSTPercentage;
}
