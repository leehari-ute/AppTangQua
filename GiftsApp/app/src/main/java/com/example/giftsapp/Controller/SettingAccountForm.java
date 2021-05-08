package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.giftsapp.Controller.Fragment_Accounts.BankAccount;
import com.example.giftsapp.Controller.Fragment_Accounts.Information;
import com.example.giftsapp.Controller.Fragment_Accounts.Introduction;
import com.example.giftsapp.Controller.Fragment_Accounts.Location;
import com.example.giftsapp.R;
import com.google.android.material.navigation.NavigationView;

public class SettingAccountForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FragmentManager fm;
    Toolbar toolbar;
    private static int FRAGMENT_INFOR = 1;
    private static int FRAGMENT_LOCATION = 2;
    private static int FRAGMENT_BANK = 3;
    private static int FRAGMENT_INTRODUCTION = 4;

    private  int currentFragment = FRAGMENT_INFOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting_account_form);
        fm = getSupportFragmentManager();
        Init();


        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Information());
    }


    private void Init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_infor){
            if(FRAGMENT_INFOR != currentFragment){
                replaceFragment(new Information());
                currentFragment = FRAGMENT_INFOR;
            }
        }
        else if(id == R.id.nav_location){
            if(FRAGMENT_LOCATION != currentFragment){
                replaceFragment(new Location());
                currentFragment = FRAGMENT_LOCATION;
            }
        }
        else if(id == R.id.nav_card){
            if(FRAGMENT_BANK != currentFragment){
                replaceFragment(new BankAccount());
                currentFragment = FRAGMENT_BANK;
            }
        }
        else if(id == R.id.nav_intro){
            if(FRAGMENT_INTRODUCTION != currentFragment){
                replaceFragment(new Introduction());
                currentFragment = FRAGMENT_INTRODUCTION;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}