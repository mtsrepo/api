package com.mts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_party_master")
public class MtsPartyMaster {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsPartyMasterId;
	@Column(name = "uuid_")
	private String uuid_;
	private Long companyId;
	private String regNo;
	private String details;
	private String GSTN;
	private String name;
}