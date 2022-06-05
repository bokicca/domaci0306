
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

    public class RequestTest {
        private WebDriver driver;
        private WebDriverWait driverWait;
        private LoginPage loginPage;
        private ManagerPage managerPage;
        private AddCustomerPage addCustomerPage;
        private OpenAccountPage openAccountPage;
        private CustomerPage customerPage;
        private Account account;
        private CustomerList customersList;

        @BeforeClass
        public void beforeClass() {
            System.setProperty("webdriver.chrome.driver", "C:\\Intel\\chromedriver_win32\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            loginPage = new LoginPage(driver, driverWait);
            managerPage = new ManagerPage(driver, driverWait);
            addCustomerPage = new AddCustomerPage(driver, driverWait);
            openAccountPage = new OpenAccountPage(driver, driverWait);
            customerPage = new CustomerPage(driver, driverWait);
            account = new Account(driver, driverWait);
            customersList = new CustomerList(driver, driverWait);
            driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        }

        @Test(priority = 1)
        public void testBankManagerLogin() {
            loginPage.clickBankManagerLoginButton();
            Assert.assertTrue(managerPage.isVisibleAddCustomerButton());
        }

        @Test(priority = 2)
        public void testAddCustomer() {
            managerPage.clickAddCustomerButton();
            Assert.assertTrue(addCustomerPage.isVisibleFirstNameField());
            addCustomerPage.inputAddCustomerFirstName("Bojana");
            addCustomerPage.inputAddCustomerLastName("Bozovic");
            addCustomerPage.inputAddCustomerPostCode("21000");
            addCustomerPage.clickCustomerSubmitButton();
            addCustomerPage.skipAlert();
            managerPage.clickCustomersListButton();
            customersList.inputCustomerNameInSearch("Bojana");
            Assert.assertTrue(customersList.isCustomerExist());
        }

        @Test(priority = 3)
        public void testOpenAccount() {
            managerPage.clickOpenAccountButton();
            openAccountPage.chooseCustomer(6);
            openAccountPage.chooseCurrency("Pound");
            openAccountPage.clickProcessButton();
            openAccountPage.skipAlert();
            managerPage.clickCustomersListButton();
            customersList.inputCustomerNameInSearch("Bojana");
            Assert.assertTrue(customersList.isacountNumberExist());
            managerPage.clickHomeButton();
        }

        @Test(priority = 4)
        public void testLogoutBankManager() {
            managerPage.clickLogoutBankManager();
            Assert.assertTrue(loginPage.isVisibleBankManagerLoginButton());
            managerPage.clickHomeButton();
        }


        @Test(priority = 5)
        public void testCustomerLogin() {
            loginPage.clickCustomerLoginButton();
            Assert.assertTrue(customerPage.isVisibleYourNameField());
            customerPage.chooseYourName(5);
            customerPage.clickLogin();
            driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Assert.assertTrue(account.isVisibleTransactionsButton());
        }

        @Test(priority = 6)
        public void testDepositOption() {
            account.clickDeposit();
            Assert.assertTrue(account.isVisibleAmountToBeDepositedField());
            account.chooseAmountToBeDeposited(5000);
            account.clickDepositButton();
            Assert.assertTrue(account.isVisibleDepositSuccessful());
        }

        @Test(priority = 7)
        public void testWithdrawnOption() {
            account.clickWithdrawl();
            Assert.assertTrue(account.isVisibleWithdrawnToBeDeposited());
            account.chooseAmountToBeWithdrawn(100);
            account.clickWithdrawButton();
            driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Assert.assertTrue(account.isVisibleTransactionSuccessful());
        }

        @Test(priority = 8)
        public void testLogoutCustomer() {
            account.clickLogoutCustomer();
            Assert.assertTrue(customerPage.isVisibleYourNameField());
        }

        @AfterClass
        public void afterClass() {
            driver.close();
        }
    }

