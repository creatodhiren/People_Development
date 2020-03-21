package com.creatoweb.peopledevelopment.agent.fragment.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.databinding.ItemDashboardLayoutBinding;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.HomeViewHolder> {
    private ItemClickListener clickListener;
    private List<String> namelist;
    private List<String> imagelist;
    private Context context;

    public DashboardAdapter(List<String> list, List<String> listimage, Context context) {
        this.namelist = list;
        this.imagelist = listimage;
        this.context = context;
        Log.v("list ", "list =" + list.toString());
    }

    public DashboardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemDashboardLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_dashboard_layout, viewGroup, false);
        return new HomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder dashboardViewHolder, int i) {

        dashboardViewHolder.binding.setName(namelist.get(i));
        dashboardViewHolder.binding.imageviewCustomdashboard.setImageResource(Integer.parseInt(imagelist.get(i)));
//        Picasso.with(context).load(ApiClient.IMAGE_URL_SLIDER + list.get(i).getCatImage()).into(dashboardViewHolder.binding.imageviewCustomdashboard);

    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ItemDashboardLayoutBinding binding;

        public HomeViewHolder(ItemDashboardLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            itemView.setOnClickListener(v -> clickListener.onClick(getAdapterPosition(), itemView));
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(int position, View view);
    }
}
