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
@Table(name = "mts_party_address")
public class MtsPartyAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsPartyAddressId;
	private Long mtsPartyMasterId;
	private Long companyId;
	private String addressCode;
	private String details;
	private String name;
	@JsonProperty("GSTN")
	private String GSTN;
	private String emailAddress;
	private String contactNumber;
}
