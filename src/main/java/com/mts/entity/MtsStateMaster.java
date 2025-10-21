package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_state_master")
public class MtsStateMaster {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsStateMasterId;
//	@Column(name = "uuid_")
//	private String uuid;
	private Long companyId;
	private String name;
	private String type;
	private String description;
	private Long createDate;
	private Long modifiedDate;

}
