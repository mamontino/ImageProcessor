package com.cft.mamontov.imageprocessor.mvp.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.source.models.TransformedImage;
import com.cft.mamontov.imageprocessor.databinding.ItemImageBinding;

import java.util.ArrayList;
import java.util.List;

class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    private static final int FIRST_VIEW_TYPE = 1;
    private static final int SECOND_VIEW_TYPE = 2;

    private Context mContext;

    private List<TransformedImage> mList = new ArrayList<>();

    void setItems(List<TransformedImage> items) {
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }

    void addItem(TransformedImage item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    ImageListAdapter(Context context) {
        this.mContext = context;

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? FIRST_VIEW_TYPE : SECOND_VIEW_TYPE;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_image, parent, false);
        return new ItemViewHolder(binding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        TransformedImage data = mList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void showProgressIndicator(boolean b) {

    }

    public void updateProcessing(int val) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemImageBinding mBinding;
        private int mViewType;

        ItemViewHolder(ItemImageBinding binding, int viewType) {
            super(binding.getRoot());
            mBinding = binding;
            mViewType = viewType;
        }

        void bind(TransformedImage data) {

            if (mViewType == FIRST_VIEW_TYPE) {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mBinding.itemImageIcon.setImageBitmap(data.getBitmap());
            mBinding.itemImageProgress.setVisibility(View.GONE);
        }
    }
}
