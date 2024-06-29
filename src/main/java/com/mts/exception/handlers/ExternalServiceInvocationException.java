package com.mts.exception.handlers;
import java.util.Map;
public class ExternalServiceInvocationException extends RuntimeException {
	private static final long serialVersionUID = 4706369090146049670L;

	protected String errCode;
	protected String errMsg;
	Map<String, Object> properties;
	
	public ExternalServiceInvocationException() {
		super();
	}

	public ExternalServiceInvocationException(String errMsg) {
		super(errMsg);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
