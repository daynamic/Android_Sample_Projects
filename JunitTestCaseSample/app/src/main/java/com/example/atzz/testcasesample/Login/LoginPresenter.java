package com.example.atzz.testcasesample.Login;

public class LoginPresenter {

    private static final int MAX_LOGIN_ATTEMPT=3;
    private int loginAttempt;

    public int incrementLoginAttempt() {
        loginAttempt=loginAttempt+1;
        return loginAttempt;
    }

    public boolean isLoginAttemptExceeded() {
        return loginAttempt >=MAX_LOGIN_ATTEMPT;
    }
}
