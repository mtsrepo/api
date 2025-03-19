package com.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.entity.MtsStatusMaster;

public interface MtsStatusMasterRepository extends JpaRepository<MtsStatusMaster, Integer>{

	MtsStatusMaster findByStatus(String type);

}
