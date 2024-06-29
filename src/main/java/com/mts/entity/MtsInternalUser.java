package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_internal_user")
public class MtsInternalUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsUserId;
	private String mtsUserName;
	private String mtsUserCode;
	private String contactNumber;
	private String emailAddress;
	private String password;
	private Long createdOn;
	private Long createdby;
	private Long modifiedOn;
	private Long modifiedBy;
	private Integer isActive;
	private Integer isDeleted;
}
