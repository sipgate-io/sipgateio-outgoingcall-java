package sipgateio.outgoingcall;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class OutgoingCall {

	private static final String baseUrl = "https://api.sipgate.com/v2";

	public static void main(String[] args) {
		Unirest.setObjectMapper(new CallObjectMapper());

		String tokenId = "YOUR_SIPGATE_TOKEN_ID";
		String token = "YOUR_SIPGATE_TOKEN";

		String caller = "DIALING_DEVICE";
		String deviceId = "YOUR_SIPGATE_DEVICE_EXTENSION";

		String callerId = "DISPLAYED_CALLER_NUMBER";
		String callee = "YOUR_RECIPIENT_PHONE_NUMBER";

		Call callObject = new Call(caller, callerId, deviceId, callee);
		try {
			HttpResponse<String> response = sendNewCallRequest(tokenId, token, callObject);
			System.out.println("Status: " + response.getStatus());
			System.out.println("Body: " + response.getBody());
		} catch (UnirestException e) {
			System.err.println(e.getMessage());
		}
	}

	private static HttpResponse<String> sendNewCallRequest(String tokenId, String token, Call callObject) throws UnirestException {
		return Unirest.post(baseUrl + "/sessions/calls")
				.basicAuth(tokenId, token)
				.header("accept", "application/json")
				.header("content-type","application/json")
				.body(callObject)
				.asString();
	}
}
