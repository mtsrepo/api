package com.mts.dataObjects;

import lombok.Data;

@Data
public class SavePartyReq {
	private String userId;
	private String authToken;
	private String regNo;
	private String details;
	private String GSTN;
	private String name; // same as company table name
	private String regAddress; // for company table
	// account -- need to ask
}