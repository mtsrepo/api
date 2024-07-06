package com.mts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_")
public class User {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(name = "uuid_")
	private String uuid;

	private Long companyId;

	private Long createDate;

	private Long modifiedDate;

	private Long roleId;

	private Long contactId;

	private String password;

	private Boolean passwordEncrypted;

	private Boolean passwordReset;

	private Long passwordModifiedDate;

	private String digest;

	private String reminderQueryQuestion;

	private String reminderQueryAnswer;

	private Integer graceLoginCount;

	private String screenName;

	private String emailAddress;

	private String contactNumber;

	private String emailOTP;

	private String mobilePhoneOTP;

	private Integer isEmailOTPVerified;

	private Integer isMobilePhoneOTPVerified;

	private Long facebookId;

	private Long ldapServerId;

	private String openId;

	private Long portraitId;

	private String languageId;

	private String timeZoneId;

	private String greeting;

	private String comments;

	private String firstName;

	private String middleName;

	private String lastName;

	private String jobTitle;

	private Long loginDate;

	private String loginIP;

	private Long lastLoginDate;

	private String lastLoginIP;

	private Long lastFailedLoginDate;

	private Integer failedLoginAttempts;

	private Boolean lockout;

	private Long lockoutDate;

	private Boolean agreedToTermsOfUse;

	private Boolean emailAddressVerified;

	private Integer status;
	
}
