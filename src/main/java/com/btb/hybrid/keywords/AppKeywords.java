package com.btb.hybrid.keywords;

public class AppKeywords extends WebGenericKeywords {
	
	public void validateLogin(){
		//test.log(status.INFO, "Validating Login");
		String expetedRes = data.get(dataKey);
		String actualRes = "";
		
		boolean res = isElementPresent("searchBox_xpath");
		if(res){
			actualRes = "LoginSuccess";
		}
		else{
			actualRes = "LoginFailure";
		}
		
		if(!expetedRes.equals(actualRes)){
			reportFailure("Got the result as :"+actualRes);
		}
	}
	
//	public void compassLogin() {
//		openBrowser();
//		type("un_id","UserName");
//		type("pwd_id","Password");
//		click("loginbutton_xpath");
//	}
	
//	public void selectSubject() {
//		selectDropDownValue("subject_id","Subject");
//	}
	
//	public void askQusWithText() {
//		type("tinymce_xpath","QuestionText");
//		click("submitQuestion_xpath");
//	}
}
