package com.todosalau.slicemaster.ui.main.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.todosalau.slicemaster.data.ProductRepository;
import com.todosalau.slicemaster.data.local.DatabaseHelper;

public class StockViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public StockViewModelFactory(Context context) {
        this.context = context;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StockViewModel.class)) {
            return (T) new StockViewModel(new ProductRepository(new DatabaseHelper(context)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
