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
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);


        mAuth = FirebaseAuth.getInstance();

        args = getArguments();

        if (args != null) {
            txtBarcodeValue.setText(args.getCharSequence("params", "No BARCODE received"));
        }


        return myview;
    }


}



