package com.mts.dataObjects;

import lombok.Data;

@Data
public class ReceiveReq {
    private Long userId;
    private String authToken;

    private Long mtsEquipMasterId;
    private Long inventoryTransactionId;
    private Long receiveLocationId;
    private Long receiveDate;

    // getters & setters
}
