package com.cft.mamontov.imageprocessor.presentation.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.databinding.ItemImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    private static final int FIRST_VIEW_TYPE = 1;
    private static final int SECOND_VIEW_TYPE = 2;

    private Context mContext;
    private final List<TransformedImage> mList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onCurrentPictureChanged(int position);
        void onItemRemoved(int position);
    }

    public void setOnItemClickListener(ImageListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void onItemSelected(int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onCurrentPictureChanged(position);
        }
    }

    private void onItemRemoved(int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemRemoved(position);
        }
    }

    private int getPositionById(int id) {
        synchronized (mList) {
            TransformedImage image;
            for (int i = 0; i < mList.size(); i++) {
                image = getItem(i);
                if (image.getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    private TransformedImage getItem(int i) {
        return mList.get(i);
    }

    public ImageListAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
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

    void addItems(List<TransformedImage> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    void addItem(TransformedImage item) {
        mList.add(0, item);
        notifyDataSetChanged();
    }

    private void removeItem(int position) {
        onItemRemoved(position);
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

    TransformedImage getCurrentItem(int position) {
        return mList.get(position);
    }

    void updateProcessing(TransformedImage image) {
        int index = getPositionById(image.getId());
        if (index < 0) {
            return;
        }
        if (image.getBitmap() != null){
            getItem(index).setBitmap(image.getBitmap());
            notifyDataSetChanged();
        }else {
            getItem(index).setProgress(image.getProgress());
        }
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
            data.setProgressBar(mBinding.itemImageProgress);

            if (mViewType == FIRST_VIEW_TYPE) {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else {
                mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }

            if (data.getBitmap() == null) {
                mBinding.itemImageProgress.setVisibility(View.VISIBLE);
                mBinding.itemImageIcon.setVisibility(View.GONE);
            } else {
                mBinding.itemImageProgress.setVisibility(View.GONE);
                mBinding.itemImageIcon.setVisibility(View.VISIBLE);
                mBinding.itemImageIcon.setImageBitmap(data.getBitmap());
                mBinding.getRoot().setOnCreateContextMenuListener(this);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.select_action);
            MenuItem edit = menu.add(Menu.NONE, 1, 1, R.string.edit);
            MenuItem delete = menu.add(Menu.NONE, 2, 2, R.string.delete);
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
