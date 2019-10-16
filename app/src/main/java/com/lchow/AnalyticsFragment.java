package com.lchow;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lchow.MyWallet.Data;
import com.lchow.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsFragment extends Fragment {


    //Firebase database..

    private FirebaseAuth mAuth;
    private DatabaseReference mExpenseDatabase;
    LineChart lineChart;

    //Recyclerview..

    private RecyclerView recyclerView;


    private TextView expenseSumResult;

    //Edt data item;

    private EditText edtAmmount;
    private EditText edtType;
    private EditText edtNote;

    private Button btnUpdate;
    private Button btnDelete;

    //Data variable..

    private String type;
    private String note;
    private int ammount;

    private String post_key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myview = inflater.inflate(R.layout.fragment_analytics, container, false);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        //PieChart data init
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Entry> xValues = new ArrayList<>();




        lineChart = (LineChart) myview.findViewById(R.id.bar_chart2);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setExtraOffsets(0,0,0,0);
        lineChart.setDragDecelerationFrictionCoef(0f);
        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);
        YAxis left = lineChart.getAxisLeft();
        left.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        left.setAxisMinValue(0f);
        myview.dispatchTouchEvent(MotionEvent.obtain(
                SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN,700,700,0)

        );





        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseDatabase").child(uid);


        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int day=1;
                int price=0;
                int i = 0;
                String day_s;
                int x = 0;
                for (DataSnapshot mysanapshot:dataSnapshot.getChildren()){

                    if(day<30){
                    Date date1 = new Date(2019, 10,day);
                    mExpenseDatabase.orderByChild("date").startAt(String.valueOf(date1)).endAt("2019-12-31");
                    Data data = mysanapshot.getValue(Data.class);
                    x = x+1;
                    price = data.getAmount();
                        yValues.add(new Entry(x,price));



                    }




                }



                // yValues.add(new PieEntry(3f,"Clothes"));
                // yValues.add(new PieEntry(55f,"Entertainment"));
                // yValues.add(new PieEntry(34f,"Basic"));
                // yValues.add(new PieEntry(55f,"Home"));
                //


                LineDataSet dataSet = new LineDataSet(yValues,"");
                //dataSet.setSliceSpace(3f);
                //dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                dataSet.setLineWidth(5);
                dataSet.disableDashedLine();

                LineData data = new LineData(dataSet);
                data.setValueTextSize(10f);
                dataSet.setDrawFilled(true);
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);



                lineChart.setData(data);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }



        });


        return myview;
}}

