package com.mts.entity;

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
//	@Column(name = "uuid_")
//	private String uuid;

	private Long mtsChallanId;

	private Long mtsEquipMasterId;

	private Long companyId;

	private String equipName;

	private String type;

	private String description;

	private Long createDate;

	private Integer qty;

	private Double valueOfGoods;

	private Double taxableValue;

	private Double iGSTPercentage;

	private Double iGSTAmount;

	private Long modifiedDate;

//	private Long createdOn;
//
//	private Long modifiedOn;

	private Integer isActive;

}
