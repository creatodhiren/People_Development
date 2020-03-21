package com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.agent.fragment.paymentlist.typelist.model.Data;
import com.creatoweb.peopledevelopment.databinding.ItemPaymentlistBinding;

import java.util.List;

public class PaymentTypeListAdapter extends RecyclerView.Adapter<PaymentTypeListAdapter.LoanViewHolder> {
    private ItemClickListener clickListener;
    private Context context;
    private ItemPaymentlistBinding binding;
    private List<Data> list;
    private String type = "";

    public PaymentTypeListAdapter(Context context, List<Data> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_paymentlist, parent, false);
        return new LoanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        if (type.equalsIgnoreCase("DDS")) {
            holder.binding.tvDate.setText(list.get(position).getInstallmentDate());
            holder.binding.tvName.setText(list.get(position).getMemberName());
            holder.binding.tvAccountcode.setText(list.get(position).getDisCode());
            holder.binding.tvDebit.setText("-");
            holder.binding.tvCredit.setText("₹ " + list.get(position).getInstallment());

        } else if (type.equalsIgnoreCase("SAVING")) {
            holder.binding.tvDate.setText(list.get(position).getPayment_date());
            holder.binding.tvName.setText(list.get(position).getClientName());
            holder.binding.tvAccountcode.setText(list.get(position).getSavingCode());
            if (list.get(position).getTransaction_type().equalsIgnoreCase("debit")) {
                holder.binding.tvDebit.setText("₹ " + list.get(position).getPaidAmount());
                holder.binding.tvCredit.setText("-");
            } else {
                holder.binding.tvCredit.setText("₹ " + list.get(position).getPaidAmount());
                holder.binding.tvDebit.setText("-");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoanViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentlistBinding binding;

        public LoanViewHolder(ItemPaymentlistBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(getAdapterPosition(), v);
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