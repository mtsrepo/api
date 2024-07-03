package com.mts.service;

import java.util.HashMap;

import org.json.JSONObject;

public interface MtsUserService {

	JSONObject getUserDetails(String idEmailContact);

	String getMd5Pass(String password);

}
