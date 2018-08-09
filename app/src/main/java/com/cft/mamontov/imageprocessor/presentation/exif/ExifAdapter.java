package com.cft.mamontov.imageprocessor.presentation.exif;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.data.models.ExifInformation;
import com.cft.mamontov.imageprocessor.databinding.ItemExifBinding;

import java.util.ArrayList;
import java.util.List;

class ExifAdapter extends RecyclerView.Adapter<ExifAdapter.ItemViewHolder> {

    private Context mContext;

    private List<ExifInformation> mList = new ArrayList<>();

    void setItems(List<ExifInformation> items) {
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }

    void addItem(ExifInformation item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    ExifAdapter(Context context) {
        this.mContext = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExifBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_exif, parent, false);
        return new ItemViewHolder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ExifInformation data = mList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemExifBinding mBinding;
        private int mViewType;

        ItemViewHolder(ItemExifBinding binding, int viewType) {
            super(binding.getRoot());
            mBinding = binding;
            mViewType = viewType;
        }

        void bind(ExifInformation data) {
            mBinding.itemExifTitle.setText("");
            mBinding.itemExifDesc.setText("");
        }
    }
}
