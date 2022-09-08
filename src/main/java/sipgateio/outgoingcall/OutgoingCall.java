package sipgateio.outgoingcall;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdimascio.dotenv.Dotenv;

public class OutgoingCall {

	private static final String baseUrl = "https://api.sipgate.com/v2";

	public static void main(String[] args) {
		Unirest.setObjectMapper(new CallObjectMapper());
		Dotenv dotenv = Dotenv.load();

		String tokenId = dotenv.get("TOKEN_ID");
		String token = dotenv.get("TOKEN");

		String caller = dotenv.get("CALLER");
		String deviceId = dotenv.get("DEVICE_ID");

		String callerId = dotenv.get("CALLER_ID");
		String callee = dotenv.get("CALLEE");

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
