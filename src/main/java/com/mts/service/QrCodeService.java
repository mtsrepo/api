package com.mts.service;

import com.mts.entity.MtsQrCode;

public interface QrCodeService {

	MtsQrCode generateAndSaveQRCode(String text);

}
