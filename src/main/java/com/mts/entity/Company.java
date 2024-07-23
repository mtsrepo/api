package com.mts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyId;

	private Long accountId;

	private Long webId;
	@Column(name = "key_")
	private Long key;

	private Long mx;

	private String name;

	private String regAddress;

	private String refDocument;

	private String homeURL;

	private Long logoId;

	@Column(name = "`system`")
	private Integer system;

	private Long maxUsers;
	@Column(name = "active_")
	private Long active;
}
