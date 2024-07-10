package com.todosalau.slicemaster.ui.login.state;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;


    public LoginFormState() {
        this.usernameError = null;
        this.passwordError = null;
    }

    public void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    public LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
    }

    public void setUsernameError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return (getUsernameError() == null) && (getPasswordError() == null);
    }
}