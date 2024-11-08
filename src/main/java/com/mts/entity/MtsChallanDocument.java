package com.mts.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "mts_challan_document")
public class MtsChallanDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mtsChallanId;
//	@Column(name = "uuid_")
//	private String uuid;

    private Long companyId;

    private String mtsChallanCode;

    private String mtsChallanQRCode;

    private Long despFrmLocationMasterId;

    private Long despToLocationMasterId;

    private Long consignorId;

    private Long consigneeId;

	private String challanName;

    private String type;
    
    private Integer completionStatus;

    private String chalanTNC;

    private String transporterName;

    private String transporterType;

    private String vehicleNo;

    private String transporterLRNO;

    private String description;

	private Long createDate;

	private Long modifiedDate;

    private Integer isActive;

	private Integer txnType;

	private String deliveryAddress;

	@Transient
	private List<MtsChallanEquipDtl> goodsForChallan;
}
