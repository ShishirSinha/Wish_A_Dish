package com.example.wishadish.ui.Settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wishadish.R;

public class SelectPrinterFrag extends Fragment {

    private Spinner printerSpinner;
    private Button ConfirmBtn;
    private String printerName;

    private sendPrinterNameInterface obj;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.select_printer_frag, container, false);

        printerSpinner = view.findViewById(R.id.spinner3);
        ConfirmBtn = view.findViewById(R.id.confirmPrinterBtn);

        obj = (sendPrinterNameInterface) getContext();

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerName = printerSpinner.getSelectedItem().toString();
                Log.e("c1", "Printer name  = "+printerName);
                obj.sendPrinterName(printerName);
                Toast.makeText(getContext(), "Printer successfully selected!", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(SelectPrinterFrag.this).commit();
            }
        });

        return view;
    }

    public interface sendPrinterNameInterface {
        void sendPrinterName(String printer);
    }
}
