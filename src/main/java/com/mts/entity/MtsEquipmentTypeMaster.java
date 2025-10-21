package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_equipment_type_master")
public class MtsEquipmentTypeMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsEquipTypeMasterId;
//	@Column(name = "uuid_")
//	private String uuid;
	private Long mtsParEquipTypeMasterId;
	private Long companyId;
	private String name;
	private String category;
	private String description;
	private Long createDate;
	private Long modifiedDate;
}
