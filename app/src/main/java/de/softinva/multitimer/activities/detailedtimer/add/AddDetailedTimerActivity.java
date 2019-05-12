package de.softinva.multitimer.activities.detailedtimer.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.detailedtimer.AddEditDetailedTimerViewObject;
import de.softinva.multitimer.classes.AppActivity;
import de.softinva.multitimer.databinding.ActivityAddeditDetailedTimerBinding;
import de.softinva.multitimer.fragments.editdurationdialog.EditDurationDialog;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.utility.EditDuration;

import static android.text.InputType.TYPE_NULL;

public class AddDetailedTimerActivity extends AppActivity<AddDetailedTimerViewModel> implements EditDuration.UpdateDurationInSecListener {
    public static final String GROUP_ID = "de.softinva.multitimer.groupId";
    EditDurationDialog editDurationDialog;

    public static void startNewActivity(String groupId, Context context) {
        Intent intent = new Intent(context, AddDetailedTimerActivity.class);
        intent.putExtra(AddDetailedTimerActivity.GROUP_ID, groupId);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEditDurationField();
    }

    protected void setupEditDurationField() {
        EditText durationField = ((ActivityAddeditDetailedTimerBinding) binding).editDuration;
        durationField.setInputType(TYPE_NULL);
        durationField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((AddEditDetailedTimerViewObject) viewObject).onClickDurationView(v);
                }

            }
        });
    }
    @Override
    protected void setActionBar() {
        setSupportActionBar(((ActivityAddeditDetailedTimerBinding) binding).appBar);
    }

    @Override
    protected void setViewObject() {
        viewObject = new AddEditDetailedTimerViewObject(false, model.getDetailedTimer(), editDurationDialog);
    }

    @Override
    protected void setHomeUpButton() {

    }

    @Override
    protected void setModel() {
        model = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AddDetailedTimerViewModel.class);
    }

    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addedit_detailed_timer);
    }

    @Override
    protected void setTitle() {
        setTitle(model.detailedTimer.getTitle());
    }

    @Override
    protected void setClassSpecificObjects() {
        createNewDetailedTimer();
        editDurationDialog = new EditDurationDialog();
        editDurationDialog.setDurationInSec(model.detailedTimer.getDurationInSec());
    }

    protected void createNewDetailedTimer() {
        DetailedTimer timer = model.createNewDetailedTimer(getIntent().getStringExtra(GROUP_ID));
        timer.setTitle(getString(R.string.new_detailed_timer));
        timer.setDescription(getString(R.string.description));
    }

    @Override
    public void updateDurationInSec(int durationInSec) {
        model.detailedTimer.setDurationInSec(durationInSec);
        editDurationDialog.setDurationInSec(durationInSec);
    }
}
