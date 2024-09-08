package com.mts.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mts.dataObjects.ScanQrReq;
import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsLocationMaster;
import com.mts.entity.MtsQrCode;
import com.mts.repository.MtsEquipmentMasterRepository;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.repository.MtsQrCodeRepository;
import com.mts.service.QrCodeService;

@Service
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	MtsQrCodeRepository mtsQrCodeRepository;
	@Autowired
	MtsEquipmentMasterRepository mtsEquipmentMasterRepository;
	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;

	@Override
	public MtsQrCode generateAndSaveQRCode(String text) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		MtsQrCode generatedQrEntity = null;
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
			String qrCodeImage = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());

			MtsQrCode qrCode = new MtsQrCode();
			qrCode.setDecodedText(text);
			qrCode.setQrCodeImage(qrCodeImage);

			generatedQrEntity = mtsQrCodeRepository.saveAndFlush(qrCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedQrEntity;
	}

	@Override
	public JSONObject getResponseFromScannedQR(ScanQrReq qrReq) {
		JSONObject result = new JSONObject();
		try {
			MtsEquipmentMaster equipment = mtsEquipmentMasterRepository
					.findByMtsEquipMasterId(Long.valueOf(qrReq.getScannedText())).get();

			MtsLocationMaster location = mtsLocationMasterRepository
					.findByMtsLocationMasterId(equipment.getMtsLocationMasterId());

			result.put("mtsEquipMasterCode", equipment.getMtsEquipMasterCode());
			result.put("mtsEquipMasterId", equipment.getMtsEquipMasterId());
			result.put("locationId", location.getMtsLocationMasterId());
			result.put("mtsLocationName", location.getMtsLocationName());
			result.put("locationType", location.getType());
			result.put("mtsEquipName", equipment.getMtsEquipName());
			result.put("description", equipment.getDescription());
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			result.put("message", "scanned qr fetch error");
			e.printStackTrace();
		}
		return result;
	}

}
