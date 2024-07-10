package com.todosalau.slicemaster.ui.login.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.databinding.FragmentLoginBinding;
import com.todosalau.slicemaster.ui.login.LoggedInUserView;
import com.todosalau.slicemaster.ui.login.LoginFormState;
import com.todosalau.slicemaster.ui.login.viewmodel.LoginViewModel;
import com.todosalau.slicemaster.ui.login.viewmodel.LoginViewModelFactory;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private NavController navController;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = findNavController(this);
        loginViewModel = new ViewModelProvider(this.requireActivity(), new LoginViewModelFactory()).get(LoginViewModel.class);
        bindForm();
        setClickActions();

        loginViewModel.getLoginFormState().observe(this.getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                binding.login.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    binding.username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    binding.password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this.getViewLifecycleOwner(), loginResult -> {
            if (loginResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
        });


    }

    private void setClickActions() {
        binding.login.setOnClickListener(view -> {
                    binding.loading.setVisibility(View.VISIBLE);
                    loginViewModel.login(binding.username.getText().toString(),
                            binding.password.getText().toString());
                }
        );
        binding.register.setOnClickListener(view -> {
            navController.navigate(R.id.action_from_login_to_register);
        });
    }

    private void bindForm() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(binding.username.getText().toString(),
                        binding.password.getText().toString());
            }
        };
        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(binding.username.getText().toString(),
                            binding.password.getText().toString());
                }
                return false;
            }
        });
    }


    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(this.requireContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(this.requireContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}