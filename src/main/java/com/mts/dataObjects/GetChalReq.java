package com.mts.dataObjects;

import lombok.Data;

@Data
public class GetChalReq {
	private String userId;
	private String authToken;
	
	private Long mtsChallanId;
}
