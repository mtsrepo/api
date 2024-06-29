package com.mts.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_equipment_master")
public class MtsEquipmentMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsEquipMasterId;
	private String uuid;
	private Long mtsEquipTypeMasterId;
	private String mtsEquipMasterCode;
	private Long companyId;
	private String name;
	private String manufacturedCompany;
	private String suppliedCompany;
	private String description;
	private LocalDateTime dateOfPurchase;
	private LocalDateTime lastDateOfWarranty;
	private LocalDateTime currentState;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
	private Integer isActive;
}
