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

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.giftsapp.Controller.Fragment_Accounts.BankAccount;
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
    private static int FRAGMENT_INFOR = 1;
    private static int FRAGMENT_LOCATION = 2;
    private static int FRAGMENT_BANK = 3;
    private static int FRAGMENT_INTRODUCTION = 4;
    private  static  int FRAGMENT_HISTORY = 5;
    private  int currentFragment = FRAGMENT_INFOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting_account_form);
        fm = getSupportFragmentManager();
        Init();

        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginForm.class));
            finish();
        }

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        ReplaceFragment(new Information());
    }

    private void Init(){
        drawerLayout    = findViewById(R.id.drawer_layout);
        navigationView  = findViewById(R.id.nav_view);
        toolbar         = findViewById(R.id.toolbar);
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
/*            case R.id.nav_historybuy:
                if(FRAGMENT_HISTORY != currentFragment){
                    ReplaceFragment(new H);
                    currentFragment = FRAGMENT_HISTORY;
                }
                break;*/
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
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    navHello.setText("Xìn chào " + document.getString("fullName"));
                }
            }
        });
    }

}