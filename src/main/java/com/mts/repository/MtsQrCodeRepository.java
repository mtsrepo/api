package com.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.entity.MtsQrCode;

public interface MtsQrCodeRepository extends JpaRepository<MtsQrCode, Long> {

}
