package com.cft.mamontov.imageprocessor.presentation.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.data.models.TransformedImage;
import com.cft.mamontov.imageprocessor.databinding.FragmentMainBinding;
import com.cft.mamontov.imageprocessor.di.IPViewModelFactory;
import com.cft.mamontov.imageprocessor.di.scope.ActivityScoped;
import com.cft.mamontov.imageprocessor.presentation.choose.ChooseFragment;
import com.cft.mamontov.imageprocessor.utils.BitmapUtils;
import com.cft.mamontov.imageprocessor.utils.tranformation.InvertColorTransformation;
import com.cft.mamontov.imageprocessor.utils.tranformation.MirrorTransformation;
import com.cft.mamontov.imageprocessor.utils.tranformation.RotateTransformation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static android.app.Activity.RESULT_OK;

@ActivityScoped
public class MainFragment extends DaggerFragment implements ImageListAdapter.OnItemClickListener {

    public static final String TAG = "MainFragment";
    public static final String MESSAGE_PROGRESS = "message progress";

    private static final int PERMISSION_CODE_CAMERA = 102;
    private static final int REQUEST_CAMERA_PICTURE = 99;
    private static final int REQUEST_GALLERY_PICTURE = 88;

    private static final int ROTATE_IMAGE = 110;
    private static final int INVERT_COLOR = 111;
    private static final int MIRROR_IMAGE = 112;

    private FragmentMainBinding mBinding;
    private CoordinatorLayout mCoordinator;
    private ImageListAdapter mAdapter;
    private ProgressDialog mProgressDialog;

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
//                    Bitmap bitmap = new BitmapUtils().getCompressedBitmap(mViewModel.getCurrentPicturePath());
                    Bitmap bitmap = BitmapUtils.getBitmap(mViewModel.getCurrentPicturePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, mBinding.imageContainer.getWidth(),
                            mBinding.imageContainer.getHeight(), true);
                    TransformedImage image = new TransformedImage(bitmap);
                    mViewModel.setCurrentPicture(image);
                    break;
                case REQUEST_GALLERY_PICTURE:
                    if (data == null || getActivity() == null) return;
                    Bitmap galleryBitmap = BitmapUtils.getBitmap(data.getData(), getActivity());
                    TransformedImage galleryImage = new TransformedImage(galleryBitmap);
                    mViewModel.setCurrentPicture(galleryImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE_CAMERA) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, getResources().getString(R.string.camera_permission_granted));
                sendCameraIntent();
            } else {
                Log.e(TAG, getResources().getString(R.string.camera_permission_denied));
            }
        }
    }

    @Override
    public void onCurrentPictureChanged(int position) {
        mViewModel.setCurrentPicture(mAdapter.getCurrentItem(position));
    }

    public void loadImage(int requestCode) {
        switch (requestCode) {
            case ChooseFragment.REQUEST_CODE_CAMERA:
                requestCameraPermission();
                break;
            case ChooseFragment.REQUEST_CODE_GALLERY:
                loadPhotoFromGallery();
                break;
        }
    }

    public void loadImage(String uri) {
        mViewModel.getImageFromUrl(uri, mBinding.mainImage);
    }

    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Snackbar.make(mCoordinator, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, view ->
                    requestPermissions(new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_CODE_CAMERA)).show();

        } else {
            requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE_CAMERA);
        }
    }

    private void sendCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File mPhotoFile;
            try {
                mPhotoFile = BitmapUtils.createImageFile(getActivity());
                mViewModel.setCurrentPicturePath(Uri.decode(mPhotoFile.getAbsolutePath()));
                BitmapUtils.addPhotoToGallery(mViewModel.getCurrentPicturePath(), getActivity());
            } catch (IOException ex) {
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

    private void showExifFragment() {
        Toast.makeText(getContext(), "showExifFragment", Toast.LENGTH_SHORT).show();
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

    private void setCurrentImage(TransformedImage image) {
        if (mViewModel.isHasImage()) {
            mBinding.addImageButton.setVisibility(View.GONE);
            mBinding.fragmentMainBtnExif.setVisibility(View.VISIBLE);
            mBinding.mainImage.setVisibility(View.VISIBLE);
            mBinding.mainImage.setImageBitmap(image.getBitmap());
        } else {
            mBinding.addImageButton.setVisibility(View.VISIBLE);
            mBinding.fragmentMainBtnExif.setVisibility(View.GONE);
            mBinding.mainImage.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressIndicator(boolean b) {
        mAdapter.showProgressIndicator(b);
    }

    private void addItems(List<TransformedImage> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addItems(list);
            mBinding.fragmentMainRv.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }

    private void addItem(TransformedImage picture) {
        mAdapter.addItem(picture);
        mBinding.fragmentMainRv.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void updateProcessing(int val) {
        mAdapter.updateProcessing(val);
    }

    private void prepareViews() {
        mCoordinator = mBinding.getRoot().findViewById(R.id.main_coordinator);
        mBinding.fragmentMainBtnExif.setOnClickListener(v -> showExifFragment());
        mBinding.addImageButton.setOnClickListener(v -> showChooseFragment());
        mBinding.mainImage.setOnClickListener(v -> showChooseFragment());
        mBinding.fragmentMainBtnInvertColors.setOnClickListener(v -> transformImage(INVERT_COLOR));
        mBinding.fragmentMainBtnMirrorImage.setOnClickListener(v -> transformImage(MIRROR_IMAGE));
        mBinding.fragmentMainBtnRotate.setOnClickListener(v -> transformImage(ROTATE_IMAGE));
        prepareProgressDialog();
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.fragmentMainRv.setLayoutManager(layoutManager);
        mBinding.fragmentMainRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImageListAdapter(getContext());
        mBinding.fragmentMainRv.setAdapter(mAdapter);
    }

    private void prepareProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    private void prepareSubscribers() {
        mViewModel.startProcessing.observe(this, this::showProgressIndicator);
        mViewModel.updateProcessing.observe(this, this::updateProcessing);
        mViewModel.updateCurrentPicture.observe(this, this::setCurrentImage);
        mViewModel.Item.observe(this, this::addItem);
        mViewModel.getHistory.observe(this, this::addItems);
        mViewModel.getImageHistory();
        setCurrentImage(mViewModel.getCurrentPicture());
    }
}
