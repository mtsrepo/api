package com.mts.dataObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveReq {
    private Long userId;
    private String authToken;

    private Long mtsEquipMasterId;
    private Long inventoryTransactionId;
    private Long receiveLocationId;
    private Long receiveDate;

    private Integer qty;
}
