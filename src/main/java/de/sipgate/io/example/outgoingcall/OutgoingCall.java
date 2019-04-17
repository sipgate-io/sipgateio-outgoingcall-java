package de.sipgate.io.example.outgoingcall;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class OutgoingCall {

	private static final String baseUrl = "https://api.sipgate.com/v2";

	public static void main(String[] args) {
		Unirest.setObjectMapper(new CallObjectMapper());

		String username = "YOUR_SIPGATE_EMAIL";
		String password = "YOUR_SIPGATE_PASSWORD";

		String caller = "DIALING_DEVICE";
		String deviceId = "YOUR_SIPGATE_DEVICE_EXTENSION";

		String callerId = "DISPLAYED_CALLER_NUMBER";
		String callee = "YOUR_RECIPIENT_PHONE_NUMBER";

		Call callObject = new Call(caller, callerId, deviceId, callee);
		try {
			HttpResponse<String> response = sendNewCallRequest(username, password, callObject);
			System.out.println("Status: " + response.getStatus());
			System.out.println("Body: " + response.getBody());
		} catch (UnirestException e) {
			System.err.println(e.getMessage());
		}
	}

	private static HttpResponse<String> sendNewCallRequest(String username, String password, Call callObject) throws UnirestException {
		return Unirest.post(baseUrl + "/sessions/calls")
				.basicAuth(username, password)
				.header("accept", "application/json")
				.header("content-type","application/json")
				.body(callObject)
				.asString();
	}
}
