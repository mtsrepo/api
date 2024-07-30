package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties
@Data
public class GoodsDto {
	private Long mtsEquipMasterId;
	private String type;
	private Integer qty;
	private Double valueOfGoods;
	private Double taxableValue;
	private Double iGSTPercentage;
}
