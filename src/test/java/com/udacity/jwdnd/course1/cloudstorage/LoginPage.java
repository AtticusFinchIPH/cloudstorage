package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "login-button")
    private WebElement loginButton;
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;
    @FindBy(id = "error-msg")
    private WebElement errorMsg;
    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;
    @FindBy(id = "signup-success-msg")
    private WebElement signupSuccessMsg;
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void clickLoginButton() {
        loginButton.click();
    }
    public void insertUserName(String userName) {
        inputUsername.sendKeys(userName);
    }
    public void insertPassword(String password) {
        inputPassword.sendKeys(password);
    }
    public void login(String userName, String password) {
        this.insertUserName(userName);
        this.insertPassword(password);
        clickLoginButton();
    }
}
