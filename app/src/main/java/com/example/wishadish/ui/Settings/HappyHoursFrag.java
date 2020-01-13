package com.example.wishadish.ui.Settings;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

import java.util.Calendar;

public class HappyHoursFrag extends Fragment {

    private EditText happyHoursDiscountET;
    private TextView happHourStartTimeBtn, happyHourEndTimeBtn;
    private Button ConfirmBtn;
    private CheckBox happyHourCheckbox;
    private LinearLayout ll, startTimeLL, endTimeLL;
    private String discount, startTime, endTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.happy_hours_frag, container, false);

        happyHoursDiscountET = view.findViewById(R.id.happyHourDiscountET);
        happyHourCheckbox = view.findViewById(R.id.happyHourCheckBox);
        happHourStartTimeBtn = view.findViewById(R.id.happyHourStartTimeTV);
        happyHourEndTimeBtn = view.findViewById(R.id.happyHourEndTimeTV);
        ConfirmBtn = view.findViewById(R.id.confirmHappyHourBtn);
        ll = view.findViewById(R.id.ll5);
        startTimeLL = view.findViewById(R.id.startTimeLL);
        endTimeLL = view.findViewById(R.id.endTimeLL);

        if(happyHourCheckbox.isChecked()){
            ll.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.GONE);
        }

        happyHourCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(happyHourCheckbox.isChecked())
                    ll.setVisibility(View.VISIBLE);
                else
                    ll.setVisibility(View.GONE);
            }
        });

        happHourStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c1","startTime clicked");

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String status = "AM";

                                if(hourOfDay > 11){
                                    status = "PM";
                                }
                                int hour_of_12_hour_format;
                                if(hourOfDay > 11){
                                    hour_of_12_hour_format = hourOfDay - 12;
                                }
                                else {
                                    hour_of_12_hour_format = hourOfDay;
                                }
                                happHourStartTimeBtn.setText(hour_of_12_hour_format + " : " + minute + " " + status);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        happyHourEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c1","endTime clicked");

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute){

                                    String status = "AM";

                                    if(hourOfDay > 11){
                                        status = "PM";
                                    }
                                    int hour_of_12_hour_format;
                                    if(hourOfDay > 11){
                                        hour_of_12_hour_format = hourOfDay - 12;
                                    }
                                    else {
                                        hour_of_12_hour_format = hourOfDay;
                                    }
                                    happyHourEndTimeBtn.setText(hour_of_12_hour_format + " : " + minute + " " + status);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discount = happyHoursDiscountET.getText().toString().trim();

                if(!discount.equals("")) {
                    Toast.makeText(getContext(), "Happy Hour started successfully!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().remove(HappyHoursFrag.this).commit();
                }
                else{
                    Toast.makeText(getContext(), "Please enter valid values!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
