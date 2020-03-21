package com.creatoweb.peopledevelopment.agent.fragment.myaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.agent.fragment.myaccount.model.Data;
import com.creatoweb.peopledevelopment.databinding.ItemMyaccountBinding;
import com.creatoweb.peopledevelopment.databinding.ItemPaymentlistBinding;

import java.util.List;

public class MyaccountAdapter extends RecyclerView.Adapter<MyaccountAdapter.MyAccountViewHolder>
{
    private  ItemClickListener clickListener;
    private Context context;
    private ItemMyaccountBinding binding;
    private List<Data> list;
    private String type="";

    public MyaccountAdapter(Context context, List<Data> list,String type) {
        this.context = context;
        this.list = list;
        this.type=type;
    }

    @NonNull
    @Override
    public MyAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_myaccount,parent,false);
        return new MyAccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAccountViewHolder holder, int position)
    {
        if (type.equalsIgnoreCase("MEMBER")) {
            holder.binding.tvId.setText(list.get(position).getMemberActId());
            holder.binding.tvName.setText(list.get(position).getMemberName());
            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }else if(type.equalsIgnoreCase("DDS"))
        {
            holder.binding.tvId.setText(list.get(position).getDdscode());
            holder.binding.tvName.setText(list.get(position).getMemberName());
            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }else if(type.equalsIgnoreCase("SAVING"))
        {
            holder.binding.tvId.setText(list.get(position).getSavingCode());
            holder.binding.tvName.setText(list.get(position).getClientName());
            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }else if(type.equalsIgnoreCase("FD"))
        {
            holder.binding.tvId.setText(list.get(position).getFdCode());
            holder.binding.tvName.setText(list.get(position).getMemberName());
            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }else if(type.equalsIgnoreCase("RD"))
        {
            holder.binding.tvId.setText(list.get(position).getRdCode());
            holder.binding.tvName.setText(list.get(position).getMemberName());
            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }else if(type.equalsIgnoreCase("LOAN"))
        {
//            holder.binding.tvId.setText(list.get(position).getSavingCode());
//            holder.binding.tvName.setText(list.get(position).getClientName());
//            holder.binding.tvMobile.setText(list.get(position).getMemberContact1());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAccountViewHolder extends RecyclerView.ViewHolder
    {
        ItemMyaccountBinding binding;

        public MyAccountViewHolder(ItemMyaccountBinding binding) {
            super(binding.getRoot());

            this.binding=binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(getAdapterPosition(),v);
                }
            });

        }
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onClick(int position, View view);
    }
}