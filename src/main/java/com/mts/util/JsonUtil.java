package com.mts.util;

import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	public static <T> JSONArray toJsonArrayOfObjects(List<T> data) {
		try {
			String jsonString = gson.toJson(data);
			return new JSONArray(jsonString);
		} catch (Exception e) {
			throw new RuntimeException("Error converting list to JSON", e);
		}
	}
}
