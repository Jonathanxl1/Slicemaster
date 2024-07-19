package com.todosalau.slicemaster.ui.main.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.databinding.FragmentDetailsBinding;
import com.todosalau.slicemaster.ui.main.EEDITION;
import com.todosalau.slicemaster.ui.main.Pizza;
import com.todosalau.slicemaster.ui.main.Size;
import com.todosalau.slicemaster.ui.main.viewmodel.DetailsViewModel;
import com.todosalau.slicemaster.ui.main.viewmodel.DetailsViewModelFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DetailsFragment extends Fragment {

    private DetailsViewModel viewModel;
    private FragmentDetailsBinding binding;
    private long id;
    private NavController navController;


    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.Base_Theme_SliceMaster,true);
        navController = findNavController(this);
        viewModel = new ViewModelProvider(this, new DetailsViewModelFactory(this.requireContext())).get(DetailsViewModel.class);
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), android.R.layout.simple_spinner_item, Arrays.stream(Size.values()).map(Enum::name).collect(Collectors.toList()));
        binding.size.setAdapter(adapter);
        viewModel.getPizza().observe(this.getViewLifecycleOwner(), pizza -> {
            binding.name.setText(pizza.getName());
            binding.ingredients.setText(pizza.getIngredients());
            binding.price.setText(pizza.getPrice().toString());
            binding.size.setSelection(pizza.getSize().ordinal());
        });

        viewModel.getMode().observe(this.getViewLifecycleOwner(), editionMode -> {
            switch (editionMode) {
                case READING:
                    binding.name.setEnabled(false);
                    binding.ingredients.setEnabled(false);
                    binding.price.setEnabled(false);
                    binding.size.setEnabled(false);
                    binding
                            .save.setVisibility(View.GONE);
                    break;
                case EDITION:
                    bindForm();
                    binding.save.setVisibility(View.VISIBLE);
                    binding.save.setOnClickListener(saveButton -> {
                        viewModel.editItem(getItem(), id);
                        navController.navigate(R.id.action_detailsToStock);

                    });
                    break;
                case CREATION:
                    bindForm();
                    binding.save.setOnClickListener(view1 -> {
                        viewModel.createItem(getItem());
                        navController.navigate(R.id.action_detailsToStock);

                    });

                    break;
            }
        });
        EEDITION mode = DetailsFragmentArgs.fromBundle(getArguments()).getMode();
        id = DetailsFragmentArgs.fromBundle(getArguments()).getId();
        viewModel.setMode(mode);
        if ((mode == EEDITION.EDITION || mode == EEDITION.READING)) {
            viewModel.fetchPizza(id);
        }
        viewModel.getProcess().observe(this.getViewLifecycleOwner(), result -> {
            if (result == null) return;
        });
    }

    private void bindForm() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateData();
            }
        };
        binding.name.addTextChangedListener(afterTextChangedListener);
        binding.ingredients.addTextChangedListener(afterTextChangedListener);
        binding.price.addTextChangedListener(afterTextChangedListener);
        binding.size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @NonNull
    private Pizza getItem() {
        Pizza product = new Pizza();
        product.setName(binding.name.getText().toString());
        product.setIngredients(binding.ingredients.getText().toString());
        String price = binding.price.getText().toString();
        if (!price.isBlank()) {
            product.setPrice(Integer.valueOf(price));
        }
        int selectedIndex = binding.size.getSelectedItemPosition();
        if (selectedIndex != -1) {
            product.setSize(Size.values()[selectedIndex]);
        }
        return product;
    }

    private void validateData() {
        viewModel.dataChanged(getItem());
    }
}