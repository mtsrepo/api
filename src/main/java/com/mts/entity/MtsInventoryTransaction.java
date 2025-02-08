package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "mts_inventory_transaction")
@Data
public class MtsInventoryTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stateTransactionId;

	private Long mtsEquipMasterId;
	
	private Long fromLocationId;
	
	private Long toLocationId;

	private Long createdBy;

	private Long createdOn;

	private Integer isActive;

	private Long modifiedBy;

	private Long modifiedOn;

}
