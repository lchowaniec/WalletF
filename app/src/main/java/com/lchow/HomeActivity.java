package com.lchow;

import android.content.Intent;
import androidx.annotation.NonNull;
import android.support.v4.app.*;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lchow.MyWallet.Data;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     private BottomNavigationView bottomNavigationView;
     private FrameLayout frameLayout;
     PieChart pieChart;


    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;


    //Fragment

    private DashBoardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;
    private AnalyticsFragment analyticsFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar=findViewById(R.id.my_toolbar);
        toolbar.setTitle("MyWallet v1.0");
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();

        bottomNavigationView=findViewById(R.id.bottomNavigationbar);
        frameLayout=findViewById(R.id.main_frame);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ArrayList<PieEntry> yValues = new ArrayList<>();

        NavigationView navigationView=findViewById(R.id.naView);
        navigationView.setNavigationItemSelectedListener(this);

        dashBoardFragment=new DashBoardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();
        analyticsFragment=new AnalyticsFragment();

        setFragment(dashBoardFragment);
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final TextView mUserEmailView = navigationView.getHeaderView(0).findViewById(R.id.user_email);
        mUserEmailView.setText(userEmail);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.dashboard:
                        setFragment(dashBoardFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.dasboard_color);
                        return true;

                    case R.id.income:
                        setFragment(incomeFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.income_coloer);
                        return true;

                    case R.id.expense:
                        setFragment(expenseFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.expense_color);
                        return true;
                    case R.id.analytics:
                        setFragment(analyticsFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.menuPrimary);
                        return true;

                        default:
                            return false;

                }
            }
        });

    }

    private void setFragment(Fragment fragment) {


        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {

        finishAffinity();
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);        }


    }


    public void displaySelectedListener(int itemId){

        Fragment fragment=null;

        switch (itemId){
            case R.id.dashboard:
                fragment=new DashBoardFragment();
                break;

            case R.id.income:

                fragment=new IncomeFragment();

                break;

            case R.id.expense:
                fragment=new ExpenseFragment();
                break;
            case R.id.analytics:
                fragment=new AnalyticsFragment();
                break;

            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

        }

        if (fragment!=null){

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();

        }

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return true;
    }
}
