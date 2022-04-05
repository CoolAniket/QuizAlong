package com.thequizapp.quizalong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemYearBinding;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.YearViewHolder> {

    //    private final Context context;
    private List<CategoriesResponse.Year> yearList = new ArrayList<>();
    private CatCategoriesAdapter.OnItemClick onItemClick;
    private CatCategoriesAdapter.OnFavouriteCheck onFavouriteCheck;

    public List<CategoriesResponse.Year> getYearList() {
        return yearList;
    }

    public void setYearList(List<CategoriesResponse.Year> yearList) {
        this.yearList.clear();
        this.yearList.addAll(yearList);
        notifyDataSetChanged();
    }
    public void setOnItemClick(CatCategoriesAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnFavouriteCheck(CatCategoriesAdapter.OnFavouriteCheck onFavouriteCheck) {
        this.onFavouriteCheck = onFavouriteCheck;
    }

    @NonNull
    @Override
    public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year, parent, false);
        return new YearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YearViewHolder holder, int position) {
        Context context = holder.binding.getRoot().getContext();
        CategoriesResponse.Year year = yearList.get(position);
        holder.setSourceName(year.getName());
        CatCategoriesAdapter adapter = new CatCategoriesAdapter();
        adapter.setCategories(year.getCategories());
        adapter.setOnItemClick(onItemClick);
        adapter.setOnFavouriteCheck(onFavouriteCheck);
        holder.setupRecyclerView(context, adapter);
    }

    @Override
    public int getItemCount() {
        return yearList == null ? 0 : yearList.size();
    }

    public void updateCategory(CategoriesResponse.Category categoriesItem, boolean checked, boolean status) {
        for (CategoriesResponse.Year year :
                yearList) {
            for (CategoriesResponse.Category cat :
                    year.getCategories()) {
                if (cat.getKey() == categoriesItem.getKey()) {
                    cat.setStar(status ? checked ? 1:0 : checked ? 0:1);
                    notifyDataSetChanged();
                    return;
                }
                }
        }
    }

    public static class YearViewHolder extends RecyclerView.ViewHolder {
        ItemYearBinding binding;
        private final TextView txtSourceName;
        private final RecyclerView rvCategory;

        public YearViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            txtSourceName = itemView.findViewById(R.id.txt_source_name);
            rvCategory = itemView.findViewById(R.id.rv_categories);
        }

        public void setSourceName(String sourceName) {
            this.txtSourceName.setText(sourceName);
        }

        public void setupRecyclerView(Context context, CatCategoriesAdapter adapter) {
            rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
            rvCategory.setAdapter(adapter);
        }
    }
}
