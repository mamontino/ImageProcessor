package com.cft.mamontov.imageprocessor.presentation.main;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.media.ExifInterface;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.bg.LoadingService;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.databinding.FragmentMainBinding;
import com.cft.mamontov.imageprocessor.di.IPViewModelFactory;
import com.cft.mamontov.imageprocessor.di.scope.ActivityScope;
import com.cft.mamontov.imageprocessor.presentation.choose.ChooseFragment;
import com.cft.mamontov.imageprocessor.presentation.exif.ExifFragment;
import com.cft.mamontov.imageprocessor.utils.AppConstants;
import com.cft.mamontov.imageprocessor.utils.bitmap.BitmapUtils;
import com.cft.mamontov.imageprocessor.utils.tranformation.InvertColorTransformation;
import com.cft.mamontov.imageprocessor.utils.tranformation.MirrorTransformation;
import com.cft.mamontov.imageprocessor.utils.tranformation.RotateTransformation;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static android.app.Activity.RESULT_OK;

@ActivityScope
public class MainFragment extends DaggerFragment {

    public static final String TAG = "MainFragment";
    public static final String PROGRESS_UPDATE = "PROGRESS_UPDATE";

    private static final int PERMISSION_CODE = 102;
    private static final int REQUEST_CAMERA_PICTURE = 99;
    private static final int REQUEST_GALLERY_PICTURE = 88;

    private static final int ROTATE_IMAGE = 110;
    private static final int INVERT_COLOR = 111;
    private static final int MIRROR_IMAGE = 112;

    private FragmentMainBinding mBinding;
    private CoordinatorLayout mCoordinator;
    private ImageListAdapter mAdapter;

    @Inject
    IPViewModelFactory mViewModelFactory;
    private MainViewModel mViewModel;

    @Inject
    ChooseFragment mChooseFragment;

    @Inject
    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        prepareViews();
        requestPermission();
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(mBinding.fragmentMainRv);
        prepareSubscribers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_PICTURE:
                    Bitmap bitmap = BitmapUtils.getBitmap(mViewModel.getCurrentPicturePath());
                    mViewModel.setCurrentPicture(bitmap);
                    break;
                case REQUEST_GALLERY_PICTURE:
                    if (data == null || getActivity() == null) return;
                    Bitmap galleryBitmap = BitmapUtils.getBitmap(data.getData(), getActivity());
                    mViewModel.setCurrentPicturePath(BitmapUtils.getPathFromUri(data.getData(), getActivity()));
                    mViewModel.setCurrentPicture(galleryBitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, getResources().getString(R.string.camera_permission_granted));
        } else {
            Log.e(TAG, getResources().getString(R.string.camera_permission_denied));
        }
    }

    private void registerReceiver() {
        if (getActivity() != null) {
            LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity());
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PROGRESS_UPDATE);
            bManager.registerReceiver(getLoadingReceiver, intentFilter);
        }
    }

    private void startImageDownload() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoadingService.class);
            intent.putExtra(LoadingService.EXTRA_SERVICE_URL, mViewModel.getUrl());
            getActivity().startService(intent);
        }
    }

    public void loadImage(int requestCode) {
        switch (requestCode) {
            case ChooseFragment.REQUEST_CODE_CAMERA:
                loadImageFromCamera();
                break;
            case ChooseFragment.REQUEST_CODE_GALLERY:
                loadPhotoFromGallery();
                break;
        }
    }

    public void loadImage(String uri) {
        mViewModel.setUrl(uri);
        registerReceiver();
        startImageDownload();
    }

    private void requestPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Snackbar.make(mCoordinator, getResources().getString(R.string.camera_access_required),
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, view ->
                    requestPermissions(new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_CODE)).show();

        } else {
            requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE);
        }
    }

    private void loadImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File mPhotoFile;
            try {
                mPhotoFile = BitmapUtils.createImageFile(getActivity());
                mViewModel.setCurrentPicturePath(Uri.decode(mPhotoFile.getAbsolutePath()));
                BitmapUtils.addPhotoToGallery(mViewModel.getCurrentPicturePath(), getActivity());
            } catch (IOException e) {
                Toast.makeText(getContext(), R.string.error_creating_file,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Uri photoURI = FileProvider.getUriForFile(getActivity(),
                    "com.cft.mamontov.fileprovider", mPhotoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PICTURE);
        }
    }

    private void loadPhotoFromGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent,
                getString(R.string.choose_new_image_message)), REQUEST_GALLERY_PICTURE);
    }

    private void transformImage(int btn) {
        if (!mViewModel.isHasImage()) {
            showChooseFragment();
        } else {
            switch (btn) {
                case ROTATE_IMAGE:
                    mViewModel.transformImage(new RotateTransformation());
                    break;
                case INVERT_COLOR:
                    mViewModel.transformImage(new InvertColorTransformation());
                    break;
                case MIRROR_IMAGE:
                    mViewModel.transformImage(new MirrorTransformation());
                    break;
            }
        }
    }

    private void showChooseFragment() {
        if (getActivity() != null) {
            mChooseFragment.show(getActivity().getFragmentManager(), mChooseFragment.getTag());
        }
    }

    private void setCurrentImage(Bitmap bitmap) {
        if (mViewModel.isHasImage()) {
            mBinding.addImageButton.setVisibility(View.GONE);
            mBinding.fragmentMainBtnExif.setVisibility(View.VISIBLE);
            mBinding.mainImage.setVisibility(View.VISIBLE);
            mBinding.mainImage.setImageBitmap(bitmap);
        } else {
            mBinding.addImageButton.setVisibility(View.VISIBLE);
            mBinding.fragmentMainBtnExif.setVisibility(View.GONE);
            mBinding.mainImage.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateProcessing(TransformedImage image) {
        mAdapter.updateProcessing(image);
        if (image.getBitmap() != null && getActivity() != null) {
            File mPhotoFile;
            try {
                mPhotoFile = BitmapUtils.createImageFile(getActivity());
                String path = Uri.decode(mPhotoFile.getAbsolutePath());
                image.setPath(path);
                BitmapUtils.addPhotoToGallery(path, getActivity());
                try {
                    ExifInterface exif = new ExifInterface(path);
                    exif.setAttribute(ExifInterface.TAG_ARTIST, AppConstants.APP_NAME);
                    exif.saveAttributes();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error loading file: " , e.getLocalizedMessage());
                }
            } catch (IOException e) {
                Toast.makeText(getContext(), R.string.error_creating_file,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addItem(TransformedImage picture) {
        mAdapter.addItem(picture);
    }

    private void prepareViews() {
        mCoordinator = mBinding.getRoot().findViewById(R.id.main_coordinator);
        mBinding.addImageButton.setOnClickListener(v -> showChooseFragment());
        mBinding.mainImage.setOnClickListener(v -> showChooseFragment());
        mBinding.mainImage.setOnLongClickListener(v -> {
            if (getActivity() != null) {
                ExifFragment.newInstance(mViewModel.getCurrentPicturePath()).show(getActivity()
                        .getSupportFragmentManager(), MainFragment.TAG);
                Log.e("show exif: ", mViewModel.getCurrentPicturePath());
                return true;
            }
            return false;
        });
        mBinding.fragmentMainBtnInvertColors.setOnClickListener(v -> transformImage(INVERT_COLOR));
        mBinding.fragmentMainBtnMirrorImage.setOnClickListener(v -> transformImage(MIRROR_IMAGE));
        mBinding.fragmentMainBtnRotate.setOnClickListener(v -> transformImage(ROTATE_IMAGE));
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.fragmentMainRv.setLayoutManager(layoutManager);
        mBinding.fragmentMainRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImageListAdapter(getContext());
        mBinding.fragmentMainRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onCurrentPictureChanged(int position) {
                mViewModel.setCurrentPicturePath(mAdapter.getCurrentItem(position).getPath());
                mViewModel.setCurrentPicture(mAdapter.getCurrentItem(position).getBitmap());
            }

            @Override
            public void onItemRemoved(int position) {
                mViewModel.removeItem(position);
            }
        });
    }

    private void prepareSubscribers() {
        mViewModel.updateProcessing.observe(this, this::updateProcessing);
        mViewModel.updateCurrentPicture.observe(this, this::setCurrentImage);
        mViewModel.postItem.observe(this, this::addItem);
        mViewModel.postError.observe(this, this::showError);
        setCurrentImage(mViewModel.getCurrentPicture());
        if (mViewModel.getList() != null) {
            mAdapter.addItems(mViewModel.getList());
        }
    }

    private BroadcastReceiver getLoadingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PROGRESS_UPDATE)) {
                boolean downloadComplete = intent.getBooleanExtra(
                        LoadingService.EXTRA_SERVICE_COMPLETE, false);
                String path = intent.getStringExtra(LoadingService.EXTRA_SERVICE_FILE_NAME);
                if (downloadComplete) {
                    Toast.makeText(getContext(), R.string.success_file_download_complete,
                            Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapUtils.getBitmap(path);
                    mViewModel.setCurrentPicturePath(path);
                    mViewModel.setCurrentPicture(bitmap);
                }
            }
        }
    };
}
