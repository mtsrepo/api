package com.mts.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_challan_equip_dtl")
public class MtsChallanEquipDtl {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsChallanEquipId;
	@Column(name = "uuid_")
	private String uuid;

	private Long mtsChallanId;

	private Long mtsEquipMasterId;

	private Long companyId;

	private String name;

	private String type;

	private String description;

	private Long createDate;

	private Integer qty;

	private BigDecimal valueOfGoods;

	private BigDecimal taxableValue;

	private BigDecimal iGSTPercentage;

	private BigDecimal iGSTAmount;

	private Long modifiedDate;

//	private Long createdOn;
//
//	private Long modifiedOn;

	private Integer isActive;

}
