package com.todosalau.slicemaster.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.todosalau.slicemaster.databinding.SimplePizzaBinding;
import com.todosalau.slicemaster.ui.main.Pizza;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private final List<Pizza> pizzas;
    private final PizzaAdapterListener listener;

    public PizzaAdapter(List<Pizza> pizzas, PizzaAdapterListener listener) {
        this.pizzas = pizzas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimplePizzaBinding binding = SimplePizzaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PizzaViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        holder.bind(pizzas.get(position));
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public void deleteItem(Pizza pizza) {
        int index = pizzas.indexOf(pizza);
        if (index != -1) {
            notifyItemRemoved(index);
        }
    }

    public void updateItem(Pizza pizza) {
        int index = pizzas.indexOf(pizza);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder {
        SimplePizzaBinding binding;
        private PizzaAdapterListener listener;

        public PizzaViewHolder(@NonNull SimplePizzaBinding binding, PizzaAdapterListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(Pizza pizza) {
            binding.getRoot().setOnClickListener(view -> {
                listener.onItemSelected(pizza);
            });
            binding.name.setText(pizza.getName());
            binding.ingredients.setText(pizza.getIngredients());
            binding.price.setText(pizza.getPrice().toString());
            binding.size.setText(pizza.getSize().name());
            binding.edit.setOnClickListener(view -> {
                listener.onEditClick(pizza);
            });
            binding.delete.setOnClickListener(view -> {
                listener.onDeleteClick(pizza);
            });
        }
    }
}


