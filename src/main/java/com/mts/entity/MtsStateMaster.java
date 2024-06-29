package com.mts.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_state_master")
public class MtsStateMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsStateMasterId;
	@Column(name = "uuid_")
	private String uuid;
	private Long companyId;
	private String name;
	private String type;
	private String description;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;

}
