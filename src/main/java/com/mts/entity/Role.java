package com.mts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "role_")
public class Role {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String roleId;
	@Column(name = "uuid_")
	private Long uuid;

	private Long companyId;

	private Long createDate;

	private Long modifiedDate;

	private String name;

	private String title;

	private String description;
	@Column(name = "type_")
	private Integer type;

	private String subtype;
}
