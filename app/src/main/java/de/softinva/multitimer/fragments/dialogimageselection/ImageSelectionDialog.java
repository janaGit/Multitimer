package de.softinva.multitimer.fragments.dialogimageselection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.abstract_classes.AppDialogFragmentDataBinding;
import de.softinva.multitimer.classes.abstract_classes.AppViewObject;
import de.softinva.multitimer.databinding.AppListBinding;
import de.softinva.multitimer.utility.AppRecyclerAdapter;

public class ImageSelectionDialog extends AppDialogFragmentDataBinding<ImageSelectionDialogViewModel> {
    TreeMap<Integer, ImageSelectionItem> items = new TreeMap<>();
    OnClickImageSelectionItem onClickImageSelectionItemActivity;
    RecyclerView.Adapter adapter;
    public static final String IMAGE_SELECTION_DIALOG = "editDurationDialog";

    private static ImageSelectionDialog instance;

    public static ImageSelectionDialog showDialog(FragmentActivity activity) {
        ImageSelectionDialog dialog = ImageSelectionDialog.getInstance(activity);
        if (!dialog.isAdded()) {
            dialog.show(activity.getSupportFragmentManager(), IMAGE_SELECTION_DIALOG);
            activity.getSupportFragmentManager().executePendingTransactions();
        }
        return dialog;
    }

    public static ImageSelectionDialog getInstance(FragmentActivity activity) {
        ImageSelectionDialog dialog = (ImageSelectionDialog) activity.getSupportFragmentManager().findFragmentByTag(IMAGE_SELECTION_DIALOG);
        if (dialog == null) {
            if (instance == null) {
                instance = new ImageSelectionDialog();
            }
            dialog = instance;
        }
        dialog.setActivity(activity);
        return dialog;
    }

    public ImageSelectionDialog() {
        super();
    }

    public void setActivity(FragmentActivity activity) {
        if (activity instanceof OnClickImageSelectionItem) {
            onClickImageSelectionItemActivity = (OnClickImageSelectionItem) activity;
        } else {
            throw new RuntimeException(getContext().toString()
                    + " must implement OnClickImageSelectionItem");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (!(binding instanceof AppListBinding)) {
            throw new Error("binding not instance of AppListBinding!");
        }
        AppListBinding binding = (AppListBinding) getBinding();
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        items.put(1, new ImageSelectionItem(ACTION_TYPE.GALLERY, getResources().getString(R.string.select_image_gallery), R.drawable.ic_photo_library_black_24dp));

        if (Build.VERSION.SDK_INT >= 19) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        items.put(2, new ImageSelectionItem(ACTION_TYPE.CAMERA, getResources().getString(R.string.select_image_camera), R.drawable.ic_photo_camera_black_24dp));
                    }
                }
            } catch (NullPointerException e) {
                logger.info("getPackageManager() throws null pointer exception!");
            }
        }
        items.put(3, new ImageSelectionItem(ACTION_TYPE.DEFAULT, getResources().getString(R.string.select_image_default), R.drawable.logo_fertig));

        adapter = new AppRecyclerAdapter<>(createViewObject(items), R.layout.dialog_image_selection);
        recyclerView.setAdapter(adapter);
        return dialog;
    }

    @Override
    protected AppViewObject setViewObject() {
        return null;
    }

    @Override
    protected Class<ImageSelectionDialogViewModel> returnModelClass() {
        return ImageSelectionDialogViewModel.class;
    }

    @Override
    protected ViewDataBinding setBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.app_list, null, false);
    }

    @Override
    protected void setClassSpecificObjects() {

    }

    public interface OnClickImageSelectionItem {
        void onClickImageSelectionItem(ACTION_TYPE actionType);

    }

    public TreeMap<Object, ImageSelectionViewObject> createViewObject(TreeMap<Integer, ImageSelectionItem> tempTimerTreeMap) {
        TreeMap<Object, ImageSelectionViewObject> runningTimerViewObjectMap = new TreeMap<>();
        for (Map.Entry<Integer, ImageSelectionItem> entry : tempTimerTreeMap.entrySet()) {
            runningTimerViewObjectMap.put(entry.getKey(), new ImageSelectionViewObject(entry.getValue(), (OnClickImageSelectionItem) getActivity(), this));
        }
        return runningTimerViewObjectMap;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        onClickImageSelectionItemActivity.onClickImageSelectionItem(ACTION_TYPE.CANCELED);
    }
}
