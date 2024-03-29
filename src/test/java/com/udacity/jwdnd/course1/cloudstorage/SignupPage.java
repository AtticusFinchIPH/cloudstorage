package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;
    @FindBy(id = "inputLastName")
    private WebElement inputLastName;
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;
    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;
    @FindBy(id = "success-msg")
    private WebElement successMsg;
    @FindBy(id = "error-msg")
    private WebElement errorMsg;
    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void signup(String firstName, String lastName, String userName, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);
        buttonSignUp.click();
    }
}
