package com.example.shopapplication.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.shopapplication.R;
import com.example.shopapplication.utilities.SettingPreferenses;


public class SettingDialogFragment extends DialogFragment {

    private Switch mSwitch;
    private RadioButton mRadioButton3, mRadioButton4, mRadioButton5, mRadioButton8, mRadioButton12,
                        mRadioButtonUserDefined;
    private EditText mEditText;
    private Button mButtonOk, mButtonCancel;

    private int isSettingOnOff = 1;
    private int intervalTime;

    public SettingDialogFragment() {
        // Required empty public constructor
    }

    public static SettingDialogFragment newInstance() {
        SettingDialogFragment fragment = new SettingDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.setting_dialog_layout, null);

        @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        AlertDialog dialog = builder.create();

        findViews(view);
        initView();
        setListeners();

        return dialog;
    }

    private void findViews(View view){
        mSwitch =  view.findViewById(R.id.switch2);
        mRadioButton3 = view.findViewById(R.id.radioButton3);
        mRadioButton4 = view.findViewById(R.id.radioButton4);
        mRadioButton5 = view.findViewById(R.id.radioButton5);
        mRadioButton8 = view.findViewById(R.id.radioButton6);
        mRadioButton12 = view.findViewById(R.id.radioButton7);
        mRadioButtonUserDefined = view.findViewById(R.id.radioButton8);
        mEditText = view.findViewById(R.id.editTextNumber);
        mButtonOk = view.findViewById(R.id.btn_ok);
        mButtonCancel = view.findViewById(R.id.btn_cancle);
    }

    private void initView(){
        mSwitch.setChecked(true);
        mRadioButton4.setChecked(true);
    }

    private void setListeners(){
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (!check){
                    isSettingOnOff = 0;
                } else {
                    isSettingOnOff = 1;
                }
            }
        });

        mRadioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = 3;
                    mRadioButton4.setChecked(false);
                    mRadioButton5.setChecked(false);
                    mRadioButton8.setChecked(false);
                    mRadioButton12.setChecked(false);
                    mRadioButtonUserDefined.setChecked(false);
                }
            }
        });

        mRadioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = 4;
                    mRadioButton3.setChecked(false);
                    mRadioButton5.setChecked(false);
                    mRadioButton8.setChecked(false);
                    mRadioButton12.setChecked(false);
                    mRadioButtonUserDefined.setChecked(false);
                }
            }
        });

        mRadioButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = 5;
                    mRadioButton3.setChecked(false);
                    mRadioButton4.setChecked(false);
                    mRadioButton8.setChecked(false);
                    mRadioButton12.setChecked(false);
                    mRadioButtonUserDefined.setChecked(false);
                }
            }
        });

        mRadioButton8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = 8;
                    mRadioButton3.setChecked(false);
                    mRadioButton4.setChecked(false);
                    mRadioButton5.setChecked(false);
                    mRadioButton12.setChecked(false);
                    mRadioButtonUserDefined.setChecked(false);
                }
            }
        });

        mRadioButton12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = 12;
                    mRadioButton3.setChecked(false);
                    mRadioButton4.setChecked(false);
                    mRadioButton5.setChecked(false);
                    mRadioButton8.setChecked(false);
                    mRadioButtonUserDefined.setChecked(false);
                }
            }
        });

        mRadioButtonUserDefined.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check){
                    intervalTime = Integer.parseInt(mEditText.getText().toString());
                    mRadioButton3.setChecked(false);
                    mRadioButton4.setChecked(false);
                    mRadioButton5.setChecked(false);
                    mRadioButton8.setChecked(false);
                    mRadioButton12.setChecked(false);
                }
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingPreferenses.putSettingOnOffPreferences(getActivity(), isSettingOnOff);
                SettingPreferenses.putTimeIntervalPreferences(getActivity(), intervalTime);
                dismiss();
            }
        });
    }
}