package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_party_details")
public class MtsPartyDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partyDetailsId;
	private String partyName; 
	private String regNo;
	private String details;
	private String regAddress; 
	
	private String address1;
	private String address2;
	private String address3;
	private Integer pincode;
	
	private String contactPerson;
	private String mobile;
	private String emailId;
	private String designation;
	private String department;
	
	private String workOrderNo;
	private String contractValue;
	private String gstIn;
}
