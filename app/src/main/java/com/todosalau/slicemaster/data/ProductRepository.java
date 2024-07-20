package com.todosalau.slicemaster.data;

import com.todosalau.slicemaster.data.local.DatabaseHelper;
import com.todosalau.slicemaster.ui.main.Pizza;

import java.util.List;

public class ProductRepository {

    private final DatabaseHelper localSource;

    public ProductRepository(DatabaseHelper dataSource) {
        this.localSource = dataSource;
    }

    public Result<Pizza> saveProduct(Pizza pizza) {
        try {
            Pizza newPizza = localSource.getProductDao().insert(pizza);
            return new Result.Success<Pizza>(pizza);
        } catch (Exception ex) {
            return new Result.Error(ex);
        }
    }

    public Result<List<Pizza>> getAll() {
        try {
            return new Result.Success<>(localSource.getProductDao().getAll());
        } catch (Exception ex) {
            return new Result.Error(ex);
        }
    }

    public Result<Pizza> updateProduct(Pizza item) {
        try {
            localSource.getProductDao().update(item);
            return new Result.Success<Pizza>(item);
        } catch (Exception ex) {
            return new Result.Error(ex);
        }

    }

    public Result<Pizza> getProductBYId(Long id) {
        try {
            return new Result.Success<Pizza>(localSource.getProductDao().getById(id));
        } catch (Exception ex) {
            return new Result.Error(ex);
        }
    }

    public Result<Pizza> delete(Pizza pizza) {
        try {
            localSource.getProductDao().delete(pizza);
            return new Result.Success<Pizza>(pizza);
        } catch (Exception ex) {
            return new Result.Error(ex);
        }
    }
}
