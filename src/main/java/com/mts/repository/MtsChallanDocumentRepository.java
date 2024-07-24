package com.mts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsChallanDocument;

public interface MtsChallanDocumentRepository extends JpaRepository<MtsChallanDocument, Long> {

	@Query(value = "select cd.mtsChallanId, cd.companyId, cd.mtsChallanCode, cd.despFrmLocationMasterId,"
			+ " cd.despToLocationMasterId, cd.consignorId, cd.consigneeId, cd.challanName,"
			+ " cd.chalanTNC, cd.transporterName, cd.transporterType, cd.vehicleNo, cd.transporterLRNO,"
			+ "	cd.description, cd.createDate, cd.modifiedDate, cd.isActive, ed.mtsChallanEquipId,"
			+ " ed.mtsEquipMasterId, ed.qty, ed.valueOfGoods, ed.taxableValue, ed.iGSTPercentage,"
			+ " ed.iGSTAmount from mts_challan_document cd, mts_challan_equip_dtl ed"
			+ " where cd.mtsChallanId = ed.mtsChallanId", nativeQuery = true)
	List<Map<String, Object>> getAllChallans();

}
