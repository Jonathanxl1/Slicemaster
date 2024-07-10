package com.todosalau.slicemaster.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.todosalau.slicemaster.MainActivity;
import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.databinding.ActivityLoginBinding;
import com.todosalau.slicemaster.ui.login.viewmodel.LoginViewModel;
import com.todosalau.slicemaster.ui.login.viewmodel.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private NavHostFragment navHostFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.login_nav_host_fragment);
        viewModel.getLoginResult().observe(this, state -> {
            if (state.getSuccess() != null) {
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }
}
