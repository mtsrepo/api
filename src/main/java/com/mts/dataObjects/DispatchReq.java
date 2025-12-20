package com.mts.dataObjects;

import lombok.Data;

@Data
public class DispatchReq {
    private Long userId;
    private String authToken;

    private Long mtsEquipMasterId;
    private Long fromLocationId;
    private Long toLocationId;
    private Long dispatchDate;

    private Long mtsChallanId;        // optional
    private Long mtsChallanEquipId;   // optional

    private Integer qty = 1;
}
