package com.klst.eorder.openTrans;

import com.klst.edoc.api.Reference;

public class SimpleId implements Reference {

	private String content;
	
	SimpleId(String content, String type) {
		setContent(content);
		setSchemeIdentifier(type);
	}
	SimpleId(String content) {
		this(content, null);
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setSchemeIdentifier(String id) {
	}

	@Override
	public String getSchemeIdentifier() {
		return null;
	}

}
