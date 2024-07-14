package com.todosalau.slicemaster.ui.main.adapter;

import com.todosalau.slicemaster.ui.main.Pizza;

public interface PizzaAdapterListener {
    void onDeleteClick(Pizza pizza);

    void onEditClick(Pizza pizza);

    void onItemSelected(Pizza pizza);
}
