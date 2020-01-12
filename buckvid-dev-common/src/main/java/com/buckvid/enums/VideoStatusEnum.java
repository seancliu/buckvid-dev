package com.buckvid.enums;

public enum VideoStatusEnum {
	
	SUCCESS(1),		// Post successfully
	BLOCKED(2);		// Blocked by admin
	
	public final int value;
	
	VideoStatusEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
