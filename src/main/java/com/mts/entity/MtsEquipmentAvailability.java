package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_equip_availability")
public class MtsEquipmentAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsEquipAvailabilityId;
	private Long mtsEquipMasterId;
	private Integer totalNo;
	private Integer inUse;
	private Integer available;
	private Long createdOn; 
	private Long modifiedOn;
	private Integer isActive;
}
