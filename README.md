<img src="https://www.sipgatedesign.com/wp-content/uploads/wort-bildmarke_positiv_2x.jpg" alt="sipgate logo" title="sipgate" align="right" height="112" width="200"/>

# sipgate.io java outgoing call example

This example will initiate an outgoing call by first calling `caller` (your sipgate phone number or extension) and then calling `callee`.

In order to demonstrate how to initiate an outgoing call, we queried the `/sessions/calls` endpoint of the sipgate REST API.

For further information regarding the sipgate REST API please visit https://api.sipgate.com/v2/doc

### Prerequisites

- JDK 8
- VoIP client

### How To Use

Navigate to the project's root directory.

Create the `.env` by copying the [`.env.example`](.env.example) and set the values according to the comment above each variable.



The token should have the following scopes:

- `sessions:calls:write`

For more information about personal access token, visit https://www.sipgate.io/rest-api/authentication#personalAccessToken.

The `DEVICE_ID` uniquely identifies the phone extension which establishes the phone connection,
this variable is needed only when the `CALLER` is a phone number and not a device extension. Further explanation is given in the section [Web Phone Extensions](#web-phone-extensions). Nevertheless you can still use both as device extension, but in this case the `DEVICE_ID` will be ignored.

Use `CALLEE` and `CALLER_ID` to set the recipient phone number and the displayed caller number respectively.

Run the application:

```bash
./gradlew run
```

##### How It Works

The following explanations lay out how the code example works. There is no need for you to change anything unless you want to do something different.

The sipgate REST API is available under the following base URL:

```java
private static final String baseUrl = "https://api.sipgate.com/v2";
```

The API expects request data in JSON format. Thus the `Content-Type` header needs to be set accordingly. You can achieve that by using the `header` method from the `Unirest` library.

```java
private static HttpResponse<String> sendNewCallRequest(String tokenId, String token, Call callObject)
	throws UnirestException {
		return Unirest.post(baseUrl + "/sessions/calls")
			.header("Accept", "application/json")
			.header("Content-Type", "application/json")
			.basicAuth(tokenId, token)
			.body(callObject)
			.asString();
}
```

The request body contains the `Call` object, which has four fields: `deviceId`, `caller`, `callee` and `callerId` as specified above.

```java
public Call(String caller, String callerId, String deviceId, String callee) {
	this.caller = caller;
	this.callerId = callerId;
	this.deviceId = deviceId;
	this.callee = callee;
}
```

We use the java package 'Unirest' for request generation and execution. The `post` method takes as argument the request URL. Headers, authorization header and the request body are generated from `header`, `basicAuth` and `body` methods respectively. The request URL consists of the base URL defined above and the endpoint `/sessions/calls`. The method `basicAuth` from the 'Unirest' package takes credentials and generates the required Basic Auth header (for more information on Basic Auth see our [code example](https://github.com/sipgate-io/sipgateio-basicauth-java)).

```java
Unirest.post(baseUrl + "/sessions/calls")
	.basicAuth(tokenId, token)
	.header("accept", "application/json")
	.header("content-type","application/json")
	.body(callObject)
	.asString();
```

> If OAuth should be used for authorization instead of Basic Auth we do not use the `.basicAuth(tokenID, token)` mehthod. Instead we set the `Authorization` header to `Bearer` followed by a space and the access token: `.header("Authorization", "Bearer " + accessToken)`. For an example application interacting with the sipgate API using OAuth see our [sipgate.io Java Oauth example](https://github.com/sipgate-io/sipgateio-oauth-java).

### Web Phone Extensions

A Web Phone Extension consists of one letter followed by a number (e.g. 'e0'). The sipgate API uses the concept of Web Phone extensions to identify devices within your account that are enabled to initiate calls.

Depending on your needs you can choose between the following phone types:

| phone type     | letter |
| -------------- | ------ |
| voip phone     | e      |
| external phone | x      |
| mobile phone   | y      |

You can find out what your extension is as follows:

1. Log into your [sipgate account](https://app.sipgate.com/login)
2. Use the sidebar to navigate to the **Phones** (_Telefone_) tab
3. Click on the device from which you want the Web Phone extension (`deviceId`)
4. The URL of the page this takes you to should have the form `https://app.sipgate.com/{...}/devices/{deviceId}` where `{deviceId}` is your Web Phone extension

### Common Issues

#### API returns 200 OK but no call gets initiated

Possible reasons are:

- your phone is not connected
- `caller` does not match your phones Web Phone extension

#### HTTP Errors

| reason                                                                                                                            | errorcode |
| --------------------------------------------------------------------------------------------------------------------------------- | :-------: |
| bad request (e.g. request body fields are empty or only contain spaces, timestamp is invalid etc.)                                |    400    |
| tokenId and/or token are wrong                                                                                                    |    401    |
| insufficient account balance                                                                                                      |    402    |
| no permission to use specified Web Phone extension (e.g. user password must be reset in [web app](https://app.sipgate.com/login)) |    403    |
| wrong REST API endpoint                                                                                                           |    404    |
| wrong request method                                                                                                              |    405    |
| wrong or missing `Content-Type` header with `application/json`                                                                    |    415    |

### Related

- [unirest documentation](http://unirest.io/java.html)

### Contact Us

Please let us know how we can improve this example.
If you have a specific feature request or found a bug, please use **Issues** or fork this repository and send a **pull request** with your improvements.

### License

This project is licensed under **The Unlicense** (see [LICENSE file](./LICENSE)).

### External Libraries

This code uses the following external libraries

- unirest:

  - Licensed under the [MIT License](https://opensource.org/licenses/MIT)
  - Website: http://unirest.io/java.html

- jackson:
  - Licensed under the [Apache-2.0](https://opensource.org/licenses/Apache-2.0)
  - Website: https://github.com/FasterXML/jackson

---

[sipgate.io](https://www.sipgate.io) | [@sipgateio](https://twitter.com/sipgateio) | [API-doc](https://api.sipgate.com/v2/doc)
