package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SavePartyReq {
	private String userId;
	private String authToken;
	
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
	
	// for update
	private Long mtsPartyMasterId;
}
