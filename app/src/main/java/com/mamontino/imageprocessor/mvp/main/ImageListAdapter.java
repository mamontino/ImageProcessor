package com.mamontino.imageprocessor.mvp.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamontino.imageprocessor.R;
import com.mamontino.imageprocessor.databinding.ItemImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    private static final int FIRST_VIEW_TYPE = 1;
    private static final int SECOND_VIEW_TYPE = 2;

    private Context mContext;

    private List<TransformedImage> mDataList = new ArrayList<>();

    void setItems(List<TransformedImage> items) {
        mDataList.clear();
        mDataList.addAll(items);
        notifyDataSetChanged();
    }

    public ImageListAdapter(Context context) {
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
        TransformedImage data = mDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
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
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            }
            mBinding.itemImageIcon.setImageBitmap(data.getBitmap());
            mBinding.itemImageProgress.setVisibility(View.GONE);
        }
    }
}
