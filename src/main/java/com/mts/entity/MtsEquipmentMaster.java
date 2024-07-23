package com.mts.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_equipment_master")
public class MtsEquipmentMaster {
	@Id
	private Long mtsEquipMasterId;
	@Column(name = "uuid_")
	private String uuid;
	private Long mtsEquipTypeMasterId;
	private String mtsEquipMasterCode;
	private Long companyId;
	private String name;
	private String manufacturedCompany;
	private String suppliedCompany;
	private Long mtsLocationMasterId;
	private String description;
	private Long dateOfPurchase;
	private Long lastDateOfWarranty;
	private LocalDateTime currentState;
	private Long createDate;
	private Long modifiedDate;
	private Integer isActive;
}
