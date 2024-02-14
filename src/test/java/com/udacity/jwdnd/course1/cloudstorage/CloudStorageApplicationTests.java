package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	@LocalServerPort
	private int port;
	private static ChromeOptions chromeOptions;
	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	@BeforeAll
	static void beforeAll() {
//		WebDriverManager.chromedriver().setup();
		/* Incompatible Chrome version, so I need to download Chromium to test locally */
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tranv\\Downloads\\Software\\chromedriver_win32\\chromedriver.exe");
		chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("C:\\Users\\tranv\\Downloads\\Software\\chrome-win64\\chrome.exe");
	}
	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver(chromeOptions);
		webDriverWait = new WebDriverWait(driver, 2);
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver, webDriverWait);
	}
	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("signup-success-msg")).getText().contains("You successfully signed up!"));
	}
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}
	/**
	 * Test signup and login flow
	 * Write a Selenium test that verifies that the home page is not accessible without logging in.
	 */
	@Test
	public void testCanNotAccessHomePageWithoutLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}
	/**
	 * Test signup and login flow
	 * Write a Selenium test that signs up a new user,
	 * logs that user in, verifies that they can access the home page,
	 * then logs out and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testSignupLoginLogout() {
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("firstName", "lastName", "userName", "password");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login("userName", "password");
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.clickLogout();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}
	/**
	 * Test adding, editing, and deleting notes
	 * Write a Selenium test that logs in an existing user,
	 * creates a note and verifies that the note details are visible in the note list.
	 */
	@Test
	public void testCreatingNote() {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login("userName", "password");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		homePage.switchToNoteTab();
		homePage.createNote("Note title", "Note description");
		webDriverWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();

		Boolean isNoteFound = homePage.hasNote("Note title", "Note description");
		Assertions.assertEquals(isNoteFound, true);
	}
	/**
	 * Test adding, editing, and deleting notes
	 * Write a Selenium test that logs in an existing user with existing notes,
	 * clicks the edit note button on an existing note,
	 * changes the note data, saves the changes,
	 * and verifies that the changes appear in the note list.
	 */
	@Test
	public void testEditingNote() {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login("userName", "password");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		homePage.switchToNoteTab();
		Boolean isNoteFound = homePage.hasNote("Note title", "Note description");
		if(isNoteFound.equals(false)) {
			homePage.createNote("Note title", "Note description");
			webDriverWait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}

		WebElement matchedNote = homePage.getNote("Note title", "Note description");
		homePage.editNote(matchedNote, "Note title", "Edited note description");

		webDriverWait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();

		Boolean isEditedNoteFound = homePage.hasNote("Note title", "Edited note description");;
		Assertions.assertEquals(isEditedNoteFound, true);
	}
	/**
	 * Test adding, editing, and deleting notes
	 * Write a Selenium test that logs in an existing user with existing notes,
	 * clicks the delete note button on an existing note,
	 * and verifies that the note no longer appears in the note list.
	 */
//	@Test
//	public void testDeletingNote() {
//		driver.get("http://localhost:" + this.port + "/login");
//		loginPage.login("userName", "password");
//		webDriverWait.until(ExpectedConditions.titleContains("Home"));
//
//	}
}
