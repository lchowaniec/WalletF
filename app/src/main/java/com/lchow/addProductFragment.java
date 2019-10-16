package com.lchow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class addProductFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;


    //Text view

    private TextView txtBarcodeValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.product_add, container, false);


        mAuth = FirebaseAuth.getInstance();
        Bundle args = getArguments();
        txtBarcodeValue = myview.findViewById(R.id.txtBarcodeValue);

        if (args != null) {
            String argStr = args.getString("params", "No Barcode Received");
            txtBarcodeValue.setText(argStr);
        }
           else {
            txtBarcodeValue.setText("BRAK");
        }


        return myview;
    }


}



