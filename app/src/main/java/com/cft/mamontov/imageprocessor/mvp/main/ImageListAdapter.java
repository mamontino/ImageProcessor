package com.cft.mamontov.imageprocessor.mvp.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.databinding.ItemImageBinding;
import com.cft.mamontov.imageprocessor.source.models.TransformedImage;

import java.util.ArrayList;
import java.util.List;

class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    private static final int FIRST_VIEW_TYPE = 1;
    private static final int SECOND_VIEW_TYPE = 2;

    private Context mContext;
    private List<TransformedImage> mList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemSelected(int position);
    }

    public void setOnItemClickListener(ImageListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void onItemSelected(int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemSelected(position);
        }
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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    void setItems(List<TransformedImage> items) {
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }

    void addItem(TransformedImage item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

    public Bitmap getCurrentItem(int position) {
        return mList.get(position).getBitmap();
    }

    public void showProgressIndicator(boolean b) {

    }

    public void updateProcessing(int val) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private ItemImageBinding mBinding;
        private int mViewType;
        private int mPosition;

        ItemViewHolder(ItemImageBinding binding, int viewType) {
            super(binding.getRoot());
            mBinding = binding;
            mViewType = viewType;
        }

        void bind(int position) {

            mPosition = position;
            TransformedImage data = mList.get(position);

            if (mViewType == FIRST_VIEW_TYPE) {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mBinding.itemImageIcon.setImageBitmap(data.getBitmap());
            mBinding.itemImageProgress.setVisibility(View.GONE);
            mBinding.getRoot().setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select action");
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = item -> {
            switch (item.getItemId()) {
                case 1:
                    onItemSelected(mPosition);
                    break;
                case 2:
                    removeItem(mPosition);
                    break;
            }
            return true;
        };

    }
}
