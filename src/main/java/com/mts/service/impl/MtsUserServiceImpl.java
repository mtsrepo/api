package com.mts.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.repository.MtsInternalUserRepository;
import com.mts.service.MtsUserService;

@Service
public class MtsUserServiceImpl implements MtsUserService {

	@Autowired
	MtsInternalUserRepository mtsInternalUserRepository;

	@Override
	public JSONObject getUserDetails(String email) {
		JSONObject result = null;

		try {
			Map<String, Object> data = mtsInternalUserRepository.findByEmailAddress(email);
			result = new JSONObject(data);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public String getMd5Pass(String password) {

		String hashtext = null;
		try {
			// static getInstance() method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// calculating message digest of an input that return array of byte
			byte[] messageDigest = md.digest(password.getBytes());
			// converting byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			// converting message digest into hex value
			hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return hashtext;

	}

}
