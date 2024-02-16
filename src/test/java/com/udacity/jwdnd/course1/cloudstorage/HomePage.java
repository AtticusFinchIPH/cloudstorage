package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriverWait webDriverWait;
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;
    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;
    @FindBy(id = "note-title")
    private WebElement noteTitleInput;
    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;
    @FindBy(id = "note-submit-button")
    private WebElement noteSubmitButton;
    @FindBy(id="noteTable")
    WebElement noteTable;
    @FindBy(id = "add-credential-button")
    WebElement addCredentialButton;
    @FindBy(id = "credential-url")
    WebElement credentialUrlInput;
    @FindBy(id = "credential-username")
    WebElement credentialUsernameInput;
    @FindBy(id = "credential-password")
    WebElement credentialPasswordInput;
    @FindBy(id = "credential-submit-button")
    WebElement credentialSubmitButton;
    @FindBy(id = "credentialTable")
    WebElement credentialTable;

    public HomePage(WebDriver driver, WebDriverWait webDriverWait){
        this.webDriverWait = webDriverWait;
        PageFactory.initElements(driver, this);
    }
    public void clickLogout(){
        logoutButton.click();
    }
    public void switchToNoteTab() {
        navNotesTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(addNoteButton));
    }
    public void createNote(String noteTitle, String noteDescription) {
        addNoteButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(noteSubmitButton));
        noteTitleInput.sendKeys(noteTitle);
        noteDescriptionInput.sendKeys(noteDescription);
        noteSubmitButton.click();
    }
    public Boolean hasNote(String noteTitle, String noteDescription) {
        List<WebElement> noteList = noteTable.findElements(By.xpath("//tbody/tr"));
        Boolean isNoteFound = noteList.stream().anyMatch((note) -> {
            if(note.getText().contains(noteTitle) && note.getText().contains(noteDescription)) {
                return true;
            }
            return false;
        });
        return isNoteFound;
    }
    public WebElement getNote(String noteTitle, String noteDescription) {
        List<WebElement> noteList = noteTable.findElements(By.xpath("//tbody/tr"));
        List<WebElement> matchedNotes = noteList.stream().filter((note) -> {
            if(note.getText().contains(noteTitle) && note.getText().contains(noteDescription)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if(matchedNotes.isEmpty()) {
            return null;
        }
        return matchedNotes.get(0);
    }
    public void editNote(WebElement matchedNote, String noteTitle, String noteDescription) {
        WebElement editButton = matchedNote.findElement(By.xpath("//td[1]/a[1]"));
        editButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(noteSubmitButton));
        noteTitleInput.clear();
        noteTitleInput.sendKeys(noteTitle);
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(noteDescription);
        noteSubmitButton.click();
    }
    public void deleteNote(WebElement matchedNote) {
        WebElement deleteButton = matchedNote.findElement(By.xpath("//td[1]/a[2]"));
        deleteButton.click();
    }
    public void switchToCredentialTab() {
        navCredentialsTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(addCredentialButton));
    }
    public void createCredential(String url, String userName, String password) {
        addCredentialButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialSubmitButton));
        credentialUrlInput.sendKeys(url);
        credentialUsernameInput.sendKeys(userName);
        credentialPasswordInput.sendKeys(password);
        credentialSubmitButton.click();
    }
    public WebElement getCredential(String url, String userName) {
        List<WebElement> credentialList = credentialTable.findElements(By.xpath("//tbody/tr"));
        Optional<WebElement> optionalMatchedCredential = credentialList.stream().filter((credential) -> {
            if(credential.getText().contains(url) && credential.getText().contains(userName)) {
                return true;
            }
            return false;
        }).findFirst();
        if(optionalMatchedCredential.isEmpty()){
            return null;
        }
        return optionalMatchedCredential.get();
    }
    public void editCredential(WebElement matchedCredential, String url, String userName, String password) {
        WebElement editButton = matchedCredential.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr/td[1]/a[1]"));
        editButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialSubmitButton));
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(url);
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(userName);
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(password);
        credentialSubmitButton.click();
    }
    public void deleteCredential(WebElement matchedCredential) {
        WebElement deleteButton = matchedCredential.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr/td[1]/a[2]"));
        deleteButton.click();
    }
}
