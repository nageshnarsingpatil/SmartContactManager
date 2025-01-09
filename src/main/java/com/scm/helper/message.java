package com.scm.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class message {
   
	private String content;
	@Builder.Default
	private  messageType type=messageType.blue;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public messageType getType() {
		return type;
	}
	public void setType(messageType type) {
		this.type = type;
	}
}
