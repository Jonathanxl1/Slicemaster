package com.todosalau.slicemaster.ui.main.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.todosalau.slicemaster.databinding.FragmentStockBinding;
import com.todosalau.slicemaster.ui.main.EEDITION;
import com.todosalau.slicemaster.ui.main.Pizza;
import com.todosalau.slicemaster.ui.main.adapter.PizzaAdapter;
import com.todosalau.slicemaster.ui.main.adapter.PizzaAdapterListener;
import com.todosalau.slicemaster.ui.main.viewmodel.StockViewModel;
import com.todosalau.slicemaster.ui.main.viewmodel.StockViewModelFactory;

public class StockFragment extends Fragment implements PizzaAdapterListener {

    private StockViewModel viewModel;
    private FragmentStockBinding binding;
    private NavController navController;

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new StockViewModelFactory(this.requireContext())).get(StockViewModel.class);
        binding = FragmentStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = findNavController(this);
        viewModel.getFilteredPizzas().observe(this.getViewLifecycleOwner(), pizzas -> {
            PizzaAdapter adapter = new PizzaAdapter(pizzas, this);
            binding.items.setAdapter(adapter);
        });
        viewModel.getProcess().observe(this.getViewLifecycleOwner(), processResult -> {
            if (processResult == null) return;
            if (processResult.getError() != null) showError(processResult.getError());
        });
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.filter(s);
                return false;
            }
        });
        binding.add.setOnClickListener(button -> {
            StockFragmentDirections.StockToDetails action = StockFragmentDirections.stockToDetails(0);
            action.setMode(EEDITION.CREATION);
            navController.navigate(action);
        });
        viewModel.fetchPizzas();
    }

    private void showError(@StringRes Integer error) {
        Toast.makeText(this.requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(Pizza pizza) {
        //Pone un popup para confirmar el borrado
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            onDeleteConfirmed(pizza);
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void onDeleteConfirmed(Pizza pizza) {
        viewModel.deletePizza(pizza);
    }

    @Override
    public void onEditClick(Pizza pizza) {
        StockFragmentDirections.StockToDetails action = StockFragmentDirections.stockToDetails(pizza.getId());
        action.setMode(EEDITION.EDITION);
        navController.navigate(action);
    }

    @Override
    public void onItemSelected(Pizza pizza) {
        StockFragmentDirections.StockToDetails action = StockFragmentDirections.stockToDetails(pizza.getId());
        action.setMode(EEDITION.READING);
        navController.navigate(action);
    }
}