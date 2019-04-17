package de.sipgate.io.example.outgoingcall;

public class Call {
	public String deviceId;
	public String caller;
	public String callerId;
	public String callee;

	public Call(String caller, String callerId, String deviceId, String callee) {
		this.caller = caller;
		this.callerId = callerId;
		this.deviceId = deviceId;
		this.callee = callee;
	}
}
