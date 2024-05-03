package beem;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.Test;

public class CognitiveSigninCode {
	private static String session;
	String Username="+18296677355";
	String Token;
	String URI = "https://dev.useline.com/graphql";
	
	@Test(priority=0)
	public void cognitiveSigninCode()
	{
		 String requestBody = "{\r\n"
                 + "  \"query\": \"mutation { cognitoSigninCode(input: { username: \\\"" + Username + "\\\", isEmail: false })}\"\r\n"
                 + "}";
		Response resp=given().header("Content-Type","application/json").header("appversion","4.0.117").
		body(requestBody).
		when().post("https://dev.useline.com/graphql").then().extract().response();
		System.out.println(resp.asString());
		JsonPath json = new JsonPath(resp.asString());
		session = json.get("data.cognitoSigninCode.Session");
		System.out.println("Session:"+session);
		
	}
	
	@Test(priority=1)
	public void cognitiveSignin()
	{
		String reqBody1="{\r\n"
				+ "  \"query\": \"mutation { cognitoSignin(input: { username: \\\""+Username+"\\\" session:\\\""+session+"\\\" code:\\\"9191\\\", isEmail:true })}\"\r\n"
				+ "}";
		Response Token_resp= given().header("Content-Type","application/json").
		body(reqBody1).
		when().post("https://dev.useline.com/graphql").then().extract().response();
		JsonPath json_token = new JsonPath(Token_resp.asString());
		Token = json_token.get("data.cognitoSignin.response.AuthenticationResult.IdToken");
		System.out.println("Token:"+Token);
	}
	
	@Test(priority=2)
	public void Getuser()
	{
		String reqBody2 = "{\r\n"
				+ "  \"query\": \"query getUser { getUser { id email firstName middleName lastName userSub phoneNumber address dob externalResources { source destinationSourceId } address1 city state zipCode address2 success aboutUs ssn lineHandle idDoc userSetting profileImage incomeDetails jobDetails identityDocuments pepDetails usagePref bbvaConsumer userLoanStatus waitListNumber userSignUpSession phoneVerified emailVerified hasActiveStripeLink requestedRatingReviews joiningRefCode signupDate userId gender maritalStatus personalInfo tncAccepted appOnboardedVersion withdrawalBlocked hasPendingPayments hasPendingSubscriptions beamStatus plaidStatus fundReturnStatus showPopup isWebSignup plaidAccountsLinked primaryBankCardStatus lotteryOnboarded lotterySettings lotteryLastDepositFailed lotteryRecurDepositFailed pendingLotteryTickets lastLogin lotteryRecurringSetup lineUnlockLevels qualifiedLoanAmount unlockedLoanAmount accessibleAmountTillDebitCardVerification lastContactChangedAt shown100Klottery userIncomeReviewed userBillsReviewed isOnSupportedAppVersion lotteryBalance repaymentDuration pinwheelLinked incomeAccountLinkedThroughPlaid userSubscriptionReviewed userTrackExpenses missingEmployerAdded socureStatus userAccountStatus lastInstantTransferAt microLineSubscribed subscriptionFees socureId meetingAvailedAt arrayAuthDoneAt arrayUserId subscriptionPlan skipArray notifyQualificationChange signupChannel emailHash latestiOSVersion latestAndroidVersion disableSkipUpdate preQualReceived preApprovalDoneAt skipBankReconnectCount lastInstantTransferFailed velocityCheckTriggered workType referralCode finicityStatus emailOtpResponse kycCheck kycFailurePlatform meetingForSubOff tokenizePhoneNumber lotteryEnabled updateDetailAfterBlock tokenizeEmail }}\"\r\n"
				+ "}";
		Response Resp_getuser=given().header("Content-Type","application/json").header("Authorization", Token).header("appversion","4.0.117").
			body(reqBody2).
				when().post("https://dev.useline.com/graphql").then().log().all().extract().response();
		System.out.println(Resp_getuser.asString());
	}
	
	@Test(enabled=false)
	public void fundSummery()
	{
		String reqBody3= "{\r\n"
				+ "  \"query\": \"query fundsSummary { fundsSummary}\"\r\n"
				+ "}";
		given().header("Content-Type","application/json").header("Authorization", Token).header("appversion", "4.0.117").
		body(reqBody3).
		when().post("https://dev.useline.com/graphql").then().log().all();
		System.out.println(Token);
	}
}
