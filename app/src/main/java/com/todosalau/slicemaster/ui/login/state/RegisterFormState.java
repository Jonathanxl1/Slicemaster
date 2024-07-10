package com.todosalau.slicemaster.ui.login.state;

import androidx.annotation.Nullable;

public class RegisterFormState extends LoginFormState {
    @Nullable
    private Integer nameError;

    public RegisterFormState() {
    }

    public RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer nameError) {
        super(usernameError, passwordError);
        this.nameError = nameError;
    }

    @Override
    public boolean isDataValid() {
        return (getUsernameError() == null) && (getPasswordError() == null) && (nameError == null);
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    public void setNameError(@Nullable Integer nameError) {
        this.nameError = nameError;
    }
}
