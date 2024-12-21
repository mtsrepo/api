package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mts.entity.MtsChallanEquipDtl;

public interface MtsChallanEquipDtlRepository extends JpaRepository<MtsChallanEquipDtl, Long> {

	List<MtsChallanEquipDtl> findByMtsChallanId(Long mtsChallanId);

	Optional<MtsChallanEquipDtl> findByMtsChallanEquipId(Long mtsChallanEquipId);

//	@Query(value = "SELECT \r\n"
//	        + "    mcd.mtsChallanId AS mcd_mtsChallanId, \r\n"
//	        + "    mcd.consignorId AS mcd_consignorId, \r\n"
//	        + "    mcd.consigneeId AS mcd_consigneeId, \r\n"
//	        + "    mcd.despFrmLocationMasterId AS mcd_despFrmLocationMasterId, \r\n"
//	        + "    mcd.despToLocationMasterId AS mcd_despToLocationMasterId, \r\n"
//	        + "    mcd.*, \r\n"
//	        + "    mce.mtsChallanId AS mce_mtsChallanId, \r\n"
//	        + "    mce.mtsEquipMasterId AS mce_mtsEquipMasterId, \r\n"
//	        + "    mce.qty AS mce_qty, \r\n"
//	        + "    mce.valueOfGoods AS mce_valueOfGoods, \r\n"
//	        + "    mce.taxableValue AS mce_taxableValue, \r\n"
//	        + "    mce.iGSTPercentage AS mce_iGSTPercentage, \r\n"
//	        + "    mce.iGSTAmount AS mce_iGSTAmount, \r\n"
//	        + "    fromLocation.mtsLocationName AS fromLocationName,\r\n"
//	        + "    toLocation.mtsLocationName AS toLocationName,\r\n"
//	        + "    consignor.partyName AS consignorName,\r\n"
//	        + "    consignee.partyName AS consigneeName\r\n"
//	        + "FROM \r\n"
//	        + "    mts_challan_document mcd\r\n"
//	        + "LEFT JOIN \r\n"
//	        + "    mts_location_master fromLocation \r\n"
//	        + "    ON mcd.despFrmLocationMasterId = fromLocation.mtsLocationMasterId\r\n"
//	        + "LEFT JOIN \r\n"
//	        + "    mts_location_master toLocation \r\n"
//	        + "    ON mcd.despToLocationMasterId = toLocation.mtsLocationMasterId\r\n"
//	        + "LEFT JOIN \r\n"
//	        + "    mts_party_master consignor \r\n"
//	        + "    ON mcd.consignorId = consignor.mtsPartyMasterId\r\n"
//	        + "LEFT JOIN \r\n"
//	        + "    mts_party_master consignee \r\n"
//	        + "    ON mcd.consigneeId = consignee.mtsPartyMasterId\r\n"
//	        + "LEFT JOIN \r\n"
//	        + "    mts_challan_equip_dtl mce\r\n"
//	        + "    ON mcd.mtsChallanId = mce.mtsChallanId\r\n"
//	        + "WHERE \r\n"
//	        + "    mcd.mtsChallanId = :mtsChallanId", nativeQuery = true)
	
	
	
	@Query(value = "SELECT \r\n"
			+ "    mcd.mtsChallanId AS mcd_mtsChallanId, \r\n"
			+ "    mcd.consignorId AS mcd_consignorId, \r\n"
			+ "    mcd.consigneeId AS mcd_consigneeId, \r\n"
			+ "    mcd.despFrmLocationMasterId AS mcd_despFrmLocationMasterId, \r\n"
			+ "    mcd.despToLocationMasterId AS mcd_despToLocationMasterId,\r\n"
			+ "    mcd.*, \r\n"
			+ "    fromAddress.addressLine1 AS fromAddress,  -- Location name from mts_party_address\r\n"
			+ "    toAddress.addressLine1 AS toAddress,      -- Location name from mts_party_address\r\n"
			+ "    consignor.partyName AS consignorName,\r\n"
			+ "    consignee.partyName AS consigneeName\r\n"
			+ "FROM \r\n"
			+ "    mts_challan_document mcd\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_party_address fromAddress \r\n"
			+ "    ON mcd.despFrmLocationMasterId = fromAddress.mtsPartyAddressId\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_party_address toAddress \r\n"
			+ "    ON mcd.despToLocationMasterId = toAddress.mtsPartyAddressId\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_party_master consignor \r\n"
			+ "    ON mcd.consignorId = consignor.mtsPartyMasterId\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_party_master consignee \r\n"
			+ "    ON mcd.consigneeId = consignee.mtsPartyMasterId\r\n"
			+ "WHERE \r\n"
			+ "    mcd.mtsChallanId = :mtsChallanId", nativeQuery = true)
	Map<String, Object> getChallanDetails(@Param("mtsChallanId") Long mtsChallanId);

	@Query(value = "select ced.mtsEquipMasterId, ced.equipName as mtsEquipName, ced.iGSTPercentage, ced.isActive, "
			+ "ced.iGSTAmount, ced.mtsChallanEquipId, ced.valueOfGoods, ced.mtsChallanId, ced.taxableValue, ced.qty, "
			+ "etm.mtsEquipTypeMasterId, etm.name as mtsEquipTypeName, em.serialNo from mts_challan_document cd, "
			+ "mts_challan_equip_dtl ced, mts_equipment_type_master etm, mts_equipment_master em "
			+ "where ced.mtsEquipMasterId = em.mtsEquipMasterId and ced.mtsChallanId = cd.mtsChallanId "
			+ " and etm.mtsEquipTypeMasterId = em.mtsEquipTypeMasterId",nativeQuery = true)
	List<Map<String, Object>> challanWithgoods(Long mtsChallanId);



}
