package com.cft.mamontov.imageprocessor.presentation.load;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.databinding.FragmentLoadImageBinding;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

public class LoadFragment extends DaggerDialogFragment {

    private FragmentLoadImageBinding mBinding;
    private OnUrlListener mOnUrlListener;

    @Inject
    public LoadFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnUrlListener = (OnUrlListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnUrlListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_load_image,
                container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.fragmentLoadBtn.setOnClickListener(v -> {
            String text = mBinding.fragmentLoadEt.getText().toString().trim();
            if (text.isEmpty()) {
                showError();
            }else {
                this.dismiss();
                mOnUrlListener.onUrlSelected(text);
            }
        });
    }

    private void showError() {
        mBinding.fragmentLoadEt.setError(getResources().getString(R.string.error_empty_url));
    }

    public interface OnUrlListener {
        void onUrlSelected(String url);
    }
}
