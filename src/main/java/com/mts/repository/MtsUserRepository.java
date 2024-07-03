package com.mts.repository;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsUser;

public interface MtsUserRepository extends JpaRepository<MtsUser, Long> {

	@Query(value = "SELECT m.mtsUserName, m.emailAddress, m.mtsUserId, m,mtsUserCode\r\n"
			+ "     m.contactNumber, m.password, r.roleId FROM mts_user_ m LEFT JOIN \r\n"
			+ "       mts_user_role_mapping rm ON m.mtsUserId = rm.userId LEFT JOIN\r\n"
			+ "       role_ r ON rm.roleId = r.roleId WHERE m.emailAddress = :idEmailContact \n"
		    + " OR m.contactNumber = :idEmailContact OR m.mtsUserCode = :idEmailContact", nativeQuery = true)
	Map<String, Object> findByIdEmailContact(String idEmailContact);

}
