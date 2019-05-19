package com.db.messageBean;

import org.springframework.stereotype.Component;

@Component
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageConfirmation {

	private String sourceCountry;
	private String serviceCode;
	private String trnNum;
	private String messageDetails;

		
	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getTrnNum() {
		return trnNum;
	}

	public void setTrnNum(String trnNum) {
		this.trnNum = trnNum;
	}

	public String getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}
	
	@Override
    public String toString() {
        return "\n MessageConfirmation { " + "\n" +
                "\t\t messageDetails : " + messageDetails + ",\n" +
                "\t\t sourceCountry : " + sourceCountry + ",\n" +
                "\t\t serviceCode : " + serviceCode + ",\n" +
                "\t\t trnNum : " + trnNum + "\n" +
                " }";
    }
	
}
