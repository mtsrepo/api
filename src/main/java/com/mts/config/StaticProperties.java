package com.mts.config;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StaticProperties {
	
	public static HashMap<String ,String> orgEvent = null;
	public static HashMap<String ,Long> orgEventConfigId = null;
	
//	public static HashMap<String ,List<UserRealTimeResponseBean>> userrealCampMap = null;
	
	public static HashMap<String ,String> userLatestRealCampMap = null;
	
	private static final Logger logger = LoggerFactory.getLogger(StaticProperties.class);
	
    public StaticProperties() {
    	//init();
	}

//	@Autowired
//	EngagementEventConfigRepository repository;
//	
//	@Autowired
//	static
//	EngagementUserCampMapRepository usercamprepository;
	
	
	
	
	
	/*@Bean
	public HashMap<String ,String> organizationEvents(){
		
		 List<EngagementEventConfig> list =  repository.getEngageMentEventConfigData();
		 logger.info("size-->"+list.size());
		
		
		   HashMap<String ,String> map = new HashMap<String ,String>(); 
			  try {
			  
			  
			  for(EngagementEventConfig config : list) 
				  map.put(config.getOrgId()+"|"+config.getEventName(),config.getEventAttribute()); 
			  
			  
			  orgEvent = map;
			  		  
			  }catch(Exception e) { 
				  e.printStackTrace(); 
			} 
			  return map;
			 
	}*/
	
	/*@Bean
	public HashMap<String ,Long> organizationConfigEventsId(){
		
		 List<EngagementEventConfig> list =  repository.getEngageMentEventConfigData();
		 logger.info("size of organizationConfigEventsId-->"+list.size());
		
		
		   HashMap<String ,Long> map = new HashMap<String ,Long>(); 
			  try {
			  
			  
			  for(EngagementEventConfig config : list) 
				  map.put(config.getOrgId()+"|"+config.getEventName(),config.getEngagementEventConfigId()); 
			  
			  
			  orgEventConfigId = map;
			  		  
			  }catch(Exception e) { 
				  e.printStackTrace(); 
			} 
			  return map;
			 
	}*/
	
	
	
	
	
	
	/*public void init() {
		userrealTimeMap = new HashMap<String ,UserRealTimeResponseEntity>();
	}*/
	
	
	
	
	
	
    
}
