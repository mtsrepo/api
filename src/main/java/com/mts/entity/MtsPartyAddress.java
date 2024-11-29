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
	private String addressLine1;	//new
	private String addressLine2;	//new
	@JsonProperty("GSTN")
	private String GSTN;
	private String emailAddress;
	private String contactNumber;
	//new
	private String contactPerson;
	private String designation;
	private String department;
	private String pincode;
	private Long createdOn;
	private Integer isActive;
	private Long modifiedOn;
}
