package com.mamontino.imageprocessor.mvp.exif;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.ItemExifBinding;
import com.mamontino.imageprocessor.databinding.ItemImageBinding;
import com.mamontino.imageprocessor.source.models.ExifInformation;
import com.mamontino.imageprocessor.source.models.TransformedImage;

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
        ItemImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
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

        ItemViewHolder(ItemImageBinding binding, int viewType) {
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
