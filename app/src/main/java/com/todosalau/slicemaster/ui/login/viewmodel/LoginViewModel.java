package com.todosalau.slicemaster.ui.login.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.data.FirebaseCallback;
import com.todosalau.slicemaster.data.LoginRepository;
import com.todosalau.slicemaster.data.model.LoggedInUser;
import com.todosalau.slicemaster.ui.login.LoggedInUserView;
import com.todosalau.slicemaster.ui.login.LoginResult;
import com.todosalau.slicemaster.ui.login.state.LoginFormState;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {

        loginRepository.login(username, password, new FirebaseCallback() {
            @Override
            public void onSuccess(LoggedInUser result) {
                loginRepository.setLoggedInUser(result);
                loginResult.setValue(new LoginResult(new LoggedInUserView(result.getDisplayName())));
            }

            @Override
            public void onError(Exception error) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });

    }

    public void loginDataChanged(String username, String password) {
        LoginFormState state = new LoginFormState();
        if (!isUserNameValid(username)) {
            state.setUsernameError(R.string.invalid_username);
        }
        if (!isPasswordValid(password)) {
            state.setPasswordError(R.string.invalid_password);
        }
        loginFormState.setValue(state);

    }


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

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}