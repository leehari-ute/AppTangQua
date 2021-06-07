package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.giftsapp.Controller.Fragment_Accounts.BankAccount;
import com.example.giftsapp.Controller.Fragment_Accounts.Bill;
import com.example.giftsapp.Controller.Fragment_Accounts.Information;
import com.example.giftsapp.Controller.Fragment_Accounts.Introduction;
import com.example.giftsapp.Controller.Fragment_Accounts.Location;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingAccountForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout            drawerLayout;
    NavigationView          navigationView;
    ActionBarDrawerToggle   toggle;
    Toolbar                 toolbar;
    TextView navHello;
    FragmentManager         fm;
    FirebaseAuth            fAuth;
    FirebaseUser            user;
    FirebaseFirestore       fStore;
    String                  userID;
    String openFragment = "Information";
    private static final int FRAGMENT_INFOR = 1;
    private static final int FRAGMENT_LOCATION = 2;
    private static final int FRAGMENT_BANK = 3;
    private static final int FRAGMENT_INTRODUCTION = 4;
    private  static final int FRAGMENT_HISTORY = 5;
    private  int currentFragment = FRAGMENT_INFOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_setting_account_form);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C4CC3")));
        fm = getSupportFragmentManager();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        Init();

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (getIntent().getStringExtra("EXTRA_DOCUMENT_OPEN") != null) {
            openFragment = getIntent().getStringExtra("EXTRA_DOCUMENT_OPEN");
        }
        switch (openFragment) {
            case "Bill":
                ReplaceFragment(new Bill());
                break;
            case "Address":
                ReplaceFragment(new Location());
                break;
            default:
                ReplaceFragment(new Information());
                break;
        }
    }

    private void Init(){
        drawerLayout    = findViewById(R.id.drawer_layout);
        navigationView  = findViewById(R.id.nav_view);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        user            = fAuth.getCurrentUser();
        userID          = user.getUid();
        View headerView = navigationView.getHeaderView(0);
        navHello = headerView.findViewById(R.id.txtHello);
        GetDataUser();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuHome) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_infor:
                if(FRAGMENT_INFOR != currentFragment){
                    ReplaceFragment(new Information());
                    currentFragment = FRAGMENT_INFOR;
                }
                break;
            case R.id.nav_location:
                if(FRAGMENT_LOCATION != currentFragment){
                    ReplaceFragment(new Location());
                    currentFragment = FRAGMENT_LOCATION;
                }
                break;
            case R.id.nav_card:
                if(FRAGMENT_BANK != currentFragment){
                    ReplaceFragment(new BankAccount());
                    currentFragment = FRAGMENT_BANK;
                }
                break;
            case R.id.nav_intro:
                if(FRAGMENT_INTRODUCTION != currentFragment){
                    ReplaceFragment(new Introduction());
                    currentFragment = FRAGMENT_INTRODUCTION;
                }
                break;
            case R.id.nav_historybuy:
                if(FRAGMENT_HISTORY != currentFragment){
                    ReplaceFragment(new Bill());
                    currentFragment = FRAGMENT_HISTORY;
                }
                break;
            case R.id.nav_logout:
                LogOut();
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginForm.class));
        finish();
    }

    private void GetDataUser() {
        fStore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        navHello.setText("Xìn chào " + document.getString("fullName"));
                    }
                }
            }
        });
    }

}