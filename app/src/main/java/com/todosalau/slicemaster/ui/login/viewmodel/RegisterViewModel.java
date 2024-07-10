package com.todosalau.slicemaster.ui.login.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.data.FirebaseCallback;
import com.todosalau.slicemaster.data.LoginRepository;
import com.todosalau.slicemaster.data.model.LoggedInUser;
import com.todosalau.slicemaster.ui.login.LoggedInUserView;
import com.todosalau.slicemaster.ui.login.LoginFormState;
import com.todosalau.slicemaster.ui.login.LoginResult;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> formState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> result = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public MutableLiveData<LoginFormState> getFormState() {
        return formState;
    }

    public MutableLiveData<LoginResult> getResult() {
        return result;
    }

    public void setResult(MutableLiveData<LoginResult> result) {
        this.result = result;
    }

    public void dataChanged(String username, String password, String displayName) {
        if (!isUserNameValid(username)) {
            formState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else if (!isNameValid(displayName)) {
            formState.setValue(new LoginFormState(null, R.string.invalid_display_name));
        } else {
            formState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    public void register(String username, String password, String displayName) {
        loginRepository.register(username, password, displayName, new FirebaseCallback() {
            @Override
            public void onSuccess(LoggedInUser result) {
                getResult().setValue(new LoginResult(new LoggedInUserView(result.getDisplayName())));
            }

            @Override
            public void onError(Exception error) {
                getResult().setValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isNameValid(String displayName) {
        return displayName != null && displayName.trim().length() > 5;
    }


}