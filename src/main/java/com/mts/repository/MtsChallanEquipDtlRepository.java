package com.mts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.entity.MtsChallanEquipDtl;

public interface MtsChallanEquipDtlRepository extends JpaRepository<MtsChallanEquipDtl, Long> {

	List<MtsChallanEquipDtl> findByMtsChallanId(Long mtsChallanId);

	Optional<MtsChallanEquipDtl> findByMtsChallanEquipId(Long mtsChallanEquipId);

}
