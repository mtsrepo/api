package com.mts.repository;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsInternalUser;

public interface MtsInternalUserRepository extends JpaRepository<MtsInternalUser, Long> {

	@Query(value = "SELECT m.mtsUserName, m.emailAddress, m.mtsUserId,\r\n"
			+ "     m.contactNumber, m.password, r.roleId \r\n" + "FROM mts_internal_user m \r\n" + "LEFT JOIN \r\n"
			+ "       mts_user_role_mapping rm ON m.mtsUserId = rm.userId \r\n" + "LEFT JOIN\r\n"
			+ "       role_ r ON rm.roleId = r.roleId\r\n" + "WHERE m.emailAddress = :email", nativeQuery = true)
	Map<String, Object> findByEmailAddress(String email);

}
