package com.lchow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lchow.MyWallet.Data;

import java.text.DateFormat;
import java.util.Date;


public class addProductFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;



    //Text view

    private TextView txtBarcodeValue;
    private EditText productname;
    private EditText ammount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.product_add, container, false);


        mAuth = FirebaseAuth.getInstance();


        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();
        mExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("ExpenseDatabase").child(uid);




        Bundle args = getArguments();
        txtBarcodeValue = myview.findViewById(R.id.textView3);
        productname = myview.findViewById(R.id.editText3);
        ammount = myview.findViewById(R.id.editText2);
        Button btnSend=myview.findViewById(R.id.button);


        if (args != null) {
            String argStr = args.getString("params", "No Barcode Received");
            txtBarcodeValue.setText(argStr);
        }
           else {
            txtBarcodeValue.setText("BRAK");
        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tmAmmount=ammount.getText().toString().trim();
//                String tmtype=type.getText().toString().trim();
                String tmtype=productname.getText().toString().trim();
                String tmnote = txtBarcodeValue.getText().toString().trim();

                if (TextUtils.isEmpty(tmAmmount)){
                    ammount.setError("Required Field..");
                    return;
                }

                int inamount=Integer.parseInt(tmAmmount);

                /* if (TextUtils.isEmpty(tmtype)){
                    type.setError("Required Field..");
                    return;
                }
            */


                String id=mExpenseDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data=new Data(inamount,tmtype,tmnote,id,mDate);
                mExpenseDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(),"Data added",Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);


            }});











        return myview;
    }


}



