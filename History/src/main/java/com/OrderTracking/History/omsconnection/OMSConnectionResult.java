package com.OrderTracking.History.omsconnection;

public class OMSConnectionResult {
	private final boolean success;
	private final String message;

	public OMSConnectionResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}