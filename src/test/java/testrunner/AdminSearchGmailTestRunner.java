package testrunner;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.LoginPage;
import setup.SetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdminSearchGmailTestRunner extends SetUp {

    Properties properties;
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }

    @BeforeTest
    public void doAdminLogin(){
        LoginPage loginpage=new LoginPage(driver);
        if(System.getProperty("email")!=null && System.getProperty("pass")!=null){
            loginpage.doLogin(System.getProperty("email"),System.getProperty("pass"));
        }
        else{
            loginpage.doLogin("admin@test.com","admin123");
        }
    }

    @Test(description = "Search updated gmail from admin dashboard")
    public void SearchGmail() throws IOException {
        getEnvVar();
        AdminDashboardPage dashboardPage=new AdminDashboardPage(driver);
        dashboardPage.searchByGmail(properties.getProperty("new_mail"));
        String displayValue=dashboardPage.tableValue.get(2).getText();
        String searchValue=properties.getProperty("new_mail");
        Assert.assertTrue(displayValue.contains(searchValue));
    }
}
