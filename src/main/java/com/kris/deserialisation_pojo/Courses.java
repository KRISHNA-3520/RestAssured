package com.kris.deserialisation_pojo;

import java.util.List;

public class Courses {
	

	private List<webAutomation> webAutomation;
	private List<api> api;
	private List<mobile> mobile;

	public List<com.kris.deserialisation_pojo.webAutomation> getWebAutomation() {
		return webAutomation;
	}

	public void setWebAutomation(List<com.kris.deserialisation_pojo.webAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}

	public List<com.kris.deserialisation_pojo.api> getApi() {
		return api;
	}

	public void setApi(List<com.kris.deserialisation_pojo.api> api) {
		this.api = api;
	}

	public List<com.kris.deserialisation_pojo.mobile> getMobile() {
		return mobile;
	}

	public void setMobile(List<com.kris.deserialisation_pojo.mobile> mobile) {
		this.mobile = mobile;
	}


}
