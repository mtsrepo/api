package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

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
