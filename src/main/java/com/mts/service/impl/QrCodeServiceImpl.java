package com.mts.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mts.entity.MtsQrCode;
import com.mts.repository.MtsQrCodeRepository;
import com.mts.service.QrCodeService;

@Service
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	MtsQrCodeRepository mtsQrCodeRepository;

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

}
