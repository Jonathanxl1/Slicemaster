package com.todosalau.slicemaster.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.todosalau.slicemaster.R;
import com.todosalau.slicemaster.data.ProductRepository;
import com.todosalau.slicemaster.data.Result;
import com.todosalau.slicemaster.ui.main.EEDITION;
import com.todosalau.slicemaster.ui.main.FormState;
import com.todosalau.slicemaster.ui.main.Pizza;
import com.todosalau.slicemaster.ui.main.ProcessResult;

import java.util.Collections;

public class DetailsViewModel extends ViewModel {
    private final ProductRepository repository;
    private final MutableLiveData<Pizza> pizza = new MutableLiveData<>();
    private final MutableLiveData<FormState> state = new MutableLiveData<>();
    private final MutableLiveData<EEDITION> mode = new MutableLiveData<>();
    private final MutableLiveData<ProcessResult> process = new MutableLiveData<>();

    public DetailsViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    public LiveData<EEDITION> getMode() {
        return mode;
    }

    public void setMode(EEDITION mode) {
        this.mode.setValue(mode);
    }

    public LiveData<Pizza> getPizza() {
        return pizza;
    }

    public LiveData<FormState> getState() {
        return state;
    }

    public LiveData<ProcessResult> getProcess() {
        return process;
    }

    public void fetchPizza(Long id) {
        Result<Pizza> readingResult = repository.getProductBYId(id);
        if (readingResult instanceof Result.Success) {
            process.setValue(new ProcessResult(Collections.emptyList()));
        } else {
            process.setValue(new ProcessResult(R.string.login_failed));
        }
    }

    public void dataChanged(Pizza product) {
        FormState state = new FormState();
        if (product.getPrice() < 2000) {
            state.addError("Price", R.string.login_failed);
        }
    }

    public void createItem(Pizza item) {
        Result<Pizza> result = repository.saveProduct(item);
        if (result instanceof Result.Success) {
            process.setValue(new ProcessResult(Collections.singletonList(item.getId())));
        } else {
            process.setValue(new ProcessResult(R.string.login_failed));
        }
    }

    public void editItem(Pizza item, long id) {
        item.setId(id);
        Result<Pizza> result = repository.updateProduct(item);
        if (result instanceof Result.Success) {
            process.setValue(new ProcessResult(Collections.singletonList(item.getId())));
        } else {
            process.setValue(new ProcessResult(R.string.login_failed));
        }
    }
}
