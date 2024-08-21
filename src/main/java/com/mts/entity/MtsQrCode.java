package com.mts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "mts_qr_code")
public class MtsQrCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mtsQrId;

	private String decodedText;

	private String qrCodeImage;
}
