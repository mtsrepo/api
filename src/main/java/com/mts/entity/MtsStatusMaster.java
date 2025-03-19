package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_status_master")
public class MtsStatusMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statusId;
	private String status;
	private Long createdBy;
	private Long createdOn;
	private Long isActive;
	private Long modifiedBy;
	private Long modifiedOn;
}
