package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_party_address")
public class MtsPartyAddress {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsPartyAddressId;
	private Long mtsPartyMasterId;
	private Long companyId;
	private String addressCode;
	private String details;
	private String name;
}
