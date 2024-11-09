package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mts.entity.MtsChallanDocument;

public interface MtsChallanDocumentRepository extends JpaRepository<MtsChallanDocument, Long> {

	@Query(value = "select cd.mtsChallanId, cd.companyId, cd.mtsChallanCode, cd.despFrmLocationMasterId,"
			+ " cd.despToLocationMasterId, cd.consignorId, cd.consigneeId, cd.challanName,"
			+ " cd.chalanTNC, cd.transporterName, cd.transporterType, cd.vehicleNo, cd.transporterLRNO,"
			+ "	cd.description, cd.createDate, cd.modifiedDate, cd.isActive, ed.mtsChallanEquipId,"
			+ " ed.mtsEquipMasterId, ed.qty, ed.valueOfGoods, ed.taxableValue, ed.iGSTPercentage,"
			+ " ed.iGSTAmount from mts_challan_document cd, mts_challan_equip_dtl ed"
			+ " where cd.mtsChallanId = ed.mtsChallanId "
			+ "order by ed.mtsChallanId LIMIT :skip , :take", nativeQuery = true)
	List<Map<String, Object>> getAllChallans(@Param("skip") int skip, @Param("take") int take);

	Optional<MtsChallanDocument> findByMtsChallanId(Long mtsChallanId);

	@Query(value = "select mtsChallanId, mtsChallanCode, challanName, createDate, "
			+ "vehicleNo, transporterName from mts_challan_document where isActive = 1 "
			+ "order by createDate desc limit :skip, :take", nativeQuery = true)
	List<Map<String, Object>> getChallanDash(@Param("take") int take, @Param("skip") int skip);

}
