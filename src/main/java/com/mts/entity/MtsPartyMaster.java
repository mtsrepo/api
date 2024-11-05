package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_party_master")
public class MtsPartyMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsPartyMasterId;
//	@Column(name = "uuid_")
//	private String uuid_;
	private Long companyId;
	private String regNo;
	private String details;
	private String partyName;
}
