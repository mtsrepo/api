package com.mts.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


@Component
public class LoginUserDetailsCache {

	
	 private static final Logger logger = LoggerFactory.getLogger(LoginUserDetailsCache.class);
	 public static volatile Map<String, String> LoginStaticData = new HashMap();
	 
	 public static volatile Map<Long, String> UserLoginControlData = new HashMap();
	 
	 
	 //@Cacheable(value = "loginStaticData", key = "#key")
//	 @Cacheable(value = "LoginStaticData", key = "#key")
     public String addStaticData(String key, String value) {
//		 logger.info(key + "__________________________________________________-");
		 if(LoginStaticData.get(key.trim()) != null) {
//			 logger.info("Somnath ......");
			 return LoginStaticData.get(key.trim());
			 
		 }else {
			 
//			 LoginStaticData.put(key.trim(), value);
			 return "SUCCESS";
		 }
      }
     
	 
	 @CacheEvict(value = "LoginStaticData", key = "#key")
     public void removeLoginData(String key) {
	      try {
	    	  LoginStaticData.remove(key.trim());
	      }catch(Exception e) {
	         // If an exception occurs (e.g., key not found in cache), do nothing.
	      }
     }
	 
	 @CacheEvict(value = "LoginStaticData", key = "#key")
     public void removeAllLoginData(String key) {
	      try {
	    	  LoginStaticData.clear();
	      }catch(Exception e) {
	         // If an exception occurs (e.g., key not found in cache), do nothing.
	      }
     }
	 
	 public Map<String, String> getAlldata(){
		 return LoginStaticData;
	 }
	 

}
