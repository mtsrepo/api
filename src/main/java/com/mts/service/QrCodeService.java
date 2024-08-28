package com.mts.service;

import org.json.JSONObject;

import com.mts.dataObjects.ScanQrReq;
import com.mts.entity.MtsQrCode;

public interface QrCodeService {

	MtsQrCode generateAndSaveQRCode(String text);

	JSONObject getResponseFromScannedQR(ScanQrReq qrReq);

}
