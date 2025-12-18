package com.mts.dataObjects;

import lombok.Data;

@Data
public class InventoryLocationReq {
    private Long userId;
    private String authToken;
    private Long locationId;

    // getters & setters
}
