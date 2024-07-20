package com.todosalau.slicemaster.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.data.ProductRepository;
import com.todosalau.slicemaster.data.Result;
import com.todosalau.slicemaster.ui.main.Pizza;
import com.todosalau.slicemaster.ui.main.ProcessResult;
import com.todosalau.slicemaster.ui.main.SingleLiveEvent;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StockViewModel extends ViewModel {
    private final MutableLiveData<List<Pizza>> filteredPizzas = new MutableLiveData<>();
    private final MutableLiveData<List<Pizza>> pizzas = new MutableLiveData<>();
    private final ProductRepository repository;
    private final MutableLiveData<ProcessResult> process = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> toastMessage = new SingleLiveEvent<>();

    public StockViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    public LiveData<ProcessResult> getProcess() {
        return process;
    }

    public LiveData<List<Pizza>> getFilteredPizzas() {
        return filteredPizzas;
    }

    public SingleLiveEvent<Integer> getToastMessage() {
        return toastMessage;
    }

    public void filter(String query) {
        if (pizzas.getValue() == null) return;
        List<Pizza> filteredList = pizzas.getValue().stream().filter(pizza -> pizza.getIngredients().toUpperCase().contains(query.toUpperCase()) || pizza.getSize().name().contains(query.toUpperCase())).collect(Collectors.toList());
        filteredPizzas.setValue(filteredList);
    }

    public void fetchPizzas() {
        Result<List<Pizza>> result = repository.getAll();
        if (result instanceof Result.Success) {
            List<Pizza> inventory = ((Result.Success<List<Pizza>>) result).getData();
            pizzas.setValue(inventory);
            filteredPizzas.setValue(inventory);
            process.setValue(new ProcessResult(Collections.emptyList()));
        } else {
            process.setValue(new ProcessResult(R.string.fetch_failed));
        }
    }

    public void deletePizza(Pizza pizza) {
        Result<Pizza> result = repository.delete(pizza);
        if (result instanceof Result.Success) {
            if (pizzas.getValue() == null) return;
            if (filteredPizzas.getValue() == null) return;
            pizzas.getValue().remove(pizza);
            pizzas.setValue(pizzas.getValue());
            filteredPizzas.getValue().remove(pizza);
            filteredPizzas.setValue(pizzas.getValue());
            toastMessage.setValue(R.string.delete_successful);
        } else {
            process.setValue(new ProcessResult(R.string.delete_failed));
        }
    }
}

