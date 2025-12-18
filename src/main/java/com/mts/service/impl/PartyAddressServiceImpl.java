package com.mts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mts.dataObjects.SavePartAddReq;
import com.mts.entity.MtsLocationMaster;
import com.mts.entity.MtsPartyAddress;
import com.mts.repository.MtsLocationMasterRepository;
import com.mts.repository.MtsPartyAddressRepository;
import com.mts.service.PartyAddressService;
import com.mts.util.JsonUtil;

@Service
public class PartyAddressServiceImpl implements PartyAddressService {

	@Autowired
	MtsPartyAddressRepository mtsPartyAddressRepository;
	@Autowired
	MtsLocationMasterRepository mtsLocationMasterRepository;

	@Value("${LOCATION_TYPE_PROJECT_SITE}")
	private int LOCATION_TYPE_PROJECT_SITE;
	@Value("${IN_TRANSIT}")
	private int IN_TRANSIT;
	
	@Override
	@Transactional
	public JSONObject savePartyAddress(SavePartAddReq partAddReq) {
		JSONObject result = new JSONObject();
		MtsPartyAddress partyAddress = null;
		MtsLocationMaster location = null;
		try {
			if (partAddReq.getMtsPartyAddressId() != null) {
				Optional<MtsPartyAddress> existingAddress = mtsPartyAddressRepository
						.findByMtsPartyAddressId(partAddReq.getMtsPartyAddressId());
				if (existingAddress.isPresent()) {
					partyAddress = existingAddress.get();
				}
			} else {
//				Optional<MtsPartyAddress> checkAlready = mtsPartyAddressRepository.findByGSTN(partAddReq.getGSTN()); 
//				checkAlready.ifPresent(existingParty -> {
//				    throw new RuntimeException("Party Address with this GSTN already exists.");
//				});
				
				partyAddress = new MtsPartyAddress();
				Random random = new Random();
				int fiveDigitNumber = 10000 + random.nextInt(90000);
				String code = "MADD" + fiveDigitNumber;

				partyAddress.setAddressCode(code);
				partyAddress.setCreatedOn(new Date().getTime());
				partyAddress.setIsActive(1);
			}

			partyAddress.setMtsPartyMasterId(partAddReq.getMtsPartyMasterId());
			partyAddress.setCompanyId(1L);		//default
			partyAddress.setAddressLine1(partAddReq.getAddressLine1());
			partyAddress.setAddressLine2(partAddReq.getAddressLine2());
			partyAddress.setLocationAddressDesc(partAddReq.getAddressLine1()+ ", "+partAddReq.getAddressLine2());
			partyAddress.setGSTN(partAddReq.getGSTN());
			partyAddress.setEmailAddress(partAddReq.getEmailAddress());
			partyAddress.setContactNumber(partAddReq.getContactNumber());
			partyAddress.setContactPerson(partAddReq.getContactPerson());
			partyAddress.setDesignation(partAddReq.getDesignation());
			partyAddress.setDepartment(partAddReq.getDepartment());
			partyAddress.setPincode(partAddReq.getPincode());
			partyAddress.setModifiedOn(new Date().getTime());

			MtsPartyAddress savedAddr = mtsPartyAddressRepository.saveAndFlush(partyAddress);
			
			location = mtsLocationMasterRepository.findByMtsPartyAddressId(savedAddr.getMtsPartyAddressId());
			
			if(location != null) {
				location.setMtsLocationName(savedAddr.getAddressLine1());
				location.setMtsPartyAddressId(savedAddr.getMtsPartyAddressId());
				location.setDescription(savedAddr.getLocationAddressDesc());
			}else {
				location = new MtsLocationMaster();
				location.setMtsLocationName(savedAddr.getAddressLine1());
				location.setMtsPartyAddressId(savedAddr.getMtsPartyAddressId());
				location.setCreateDate(new Date().getTime());
				location.setIsActive(1);
				location.setType(LOCATION_TYPE_PROJECT_SITE);
				location.setDescription(savedAddr.getLocationAddressDesc());
			}
			
			if (location.getType() == IN_TRANSIT) {
			    throw new RuntimeException("IN_TRANSIT location cannot be tied to party address");
			}
			
			mtsLocationMasterRepository.saveAndFlush(location);

			result.put("message", "Party Address saved successfully");
			result.put("status", 1);
		} catch (Exception e) {
			result.put("message", e.getMessage());
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject getAllPartyAddresses() {
		JSONObject result = new JSONObject();
		try {
			List<Map<String, Object>> data = mtsPartyAddressRepository.getAllPartyAddresses();
			result.put("data", JsonUtil.toJsonArrayOfObjects(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartyMasterIdAddress(Long mtsPartyMasterId) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = mtsPartyAddressRepository.getAddressByPartyId(mtsPartyMasterId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
