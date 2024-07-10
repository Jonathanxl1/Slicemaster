package com.todosalau.slicemaster.ui.login.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.databinding.FragmentRegisterBinding;
import com.todosalau.slicemaster.ui.login.LoggedInUserView;
import com.todosalau.slicemaster.ui.login.viewmodel.RegisterViewModel;
import com.todosalau.slicemaster.ui.login.viewmodel.RegisterViewModelFactory;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;
    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new RegisterViewModelFactory()).get(RegisterViewModel.class);
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindForm();
        viewModel.getFormState().observe(this.getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            binding.register.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                binding.username.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                binding.password.setError(getString(loginFormState.getPasswordError()));
            }
        });

        viewModel.getResult().observe(this.getViewLifecycleOwner(), loginResult -> {
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

    private void bindForm() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.dataChanged(binding.username.getText().toString(),
                        binding.password.getText().toString(),
                        binding.displayName.getText().toString());
            }
        };
        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.displayName.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.dataChanged(binding.username.getText().toString(),
                        binding.password.getText().toString(), binding.displayName.getText().toString());
            }
            return false;
        });

        binding.register.setOnClickListener(v -> {
            binding.loading.setVisibility(View.VISIBLE);
            viewModel.register(
                    binding.username.getText().toString(),
                    binding.password.getText().toString(),
                    binding.displayName.getText().toString());
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful signup
        Toast.makeText(this.requireContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(this.requireContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}