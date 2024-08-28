package com.mts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_location_master")
public class MtsLocationMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsLocationMasterId;
	@Column(name = "uuid_")
	private String uuid;
	private Long companyId;
	private String mtsLocationName;
	private String type;
	private String description;
	private Long createDate;
	private Long modifiedDate;
	private Integer isActive;
}
