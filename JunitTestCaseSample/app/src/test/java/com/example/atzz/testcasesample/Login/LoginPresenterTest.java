package com.example.atzz.testcasesample.Login;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginPresenterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void checkIfLoginAttemptIsExceeded(){
        LoginPresenter loginPresenter=new LoginPresenter();
        Assert.assertEquals(1, loginPresenter.incrementLoginAttempt());
        Assert.assertEquals(2, loginPresenter.incrementLoginAttempt());
        Assert.assertEquals(3, loginPresenter.incrementLoginAttempt());
        Assert.assertTrue(loginPresenter.isLoginAttemptExceeded());

    }

}