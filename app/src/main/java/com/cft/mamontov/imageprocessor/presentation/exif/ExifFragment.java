package com.cft.mamontov.imageprocessor.presentation.exif;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cft.mamontov.imageprocessor.databinding.FragmentExifBinding;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.utils.ExifUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

public class ExifFragment extends DaggerDialogFragment {

    private FragmentExifBinding mBinding;

    @Inject
    public ExifFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exif, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {

//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("foo", "bar");
//        list.add(map);

        Uri uri = Uri.parse("ojhlsihdvbkadg");
        ArrayList<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>(ExifUtils.getExifInfo(getActivity().getContentResolver(), uri));
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            list.add((Map<String, String>) entry);
        }
    }
}

