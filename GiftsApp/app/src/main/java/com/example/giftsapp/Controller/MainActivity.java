package com.example.giftsapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftsapp.Controller.Fragment_Accounts.Bill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.giftsapp.Controller.LoginForm.currentUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;

    private static final int HOME_FRAGMENT=0;
    private static final  int CART_FRAGMENT=1;
    private static final int ORDERS_FRAGMENT=2;
    private  static final int ACCOUNT_FRAGMENT=3;
    public static Boolean showCart = false;

    private FrameLayout frameLayout;
    private  int currentFragment =-1;
    private NavigationView navigationView;

    private TextView Username, Email;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        firebaseFirestore = FirebaseFirestore.getInstance();
        frameLayout = findViewById(R.id.main_framelayout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_order:
                        Intent intent = new Intent(getApplicationContext(), SettingAccountForm.class);
                        intent.putExtra("EXTRA_DOCUMENT_OPEN", "Bill");
                        startActivity(intent);
                        finish();
                        //GotoFragment("My Order",new MyOrdersFragment(),ORDERS_FRAGMENT);
                        break;
                    case R.id.nav_discount:
                        break;
                    case R.id.nav_account:
                        //gotoFragment("My Account",new MyAccountFragment(),ACCOUNT_FRAGMENT);
                        startActivity(new Intent(getApplicationContext(), SettingAccountForm.class));
                        finish();
                        break;
                    case R.id.nav_cart:
                        showCart = false;
                        GotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
                        break;
                    case R.id.nav_logout:
                        LogOut();
                        break;
                    case R.id.nav_home:
                        invalidateOptionsMenu();
                        SetFragment(new HomeFragment(),HOME_FRAGMENT);
                        break;
                    default:
                        break;
                }
               /*drawer.closeDrawer(GravityCompat.START);
                return true;*/
                return false;
            }
        });
        navigationView.getMenu().getItem(0).setChecked(true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();*/

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

        View navigationHeader = navigationView.getHeaderView(0);
        Username = (TextView) navigationHeader.findViewById(R.id.main_fullname);
        Email = (TextView) navigationHeader.findViewById(R.id.main_email);
        try {
            DocumentReference docRef = firebaseFirestore.collection("Users").document(currentUser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getData().get("fullName").toString();
                            String email = documentSnapshot.getData().get("email").toString();
                            Username.setText(name);
                            String sub = email.substring(0, 4);
                            Email.setText(sub + "***gmail.com");
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }catch (Exception e)
        {
            Intent intent = new Intent(getApplicationContext(), LoginForm.class);
            startActivity(intent);
            finish();
        }

        if(showCart)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            GotoFragment("My Cart",new MyCartFragment(),-2);
        } else {
            drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
            SetFragment(new HomeFragment(),HOME_FRAGMENT);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            if(currentFragment == HOME_FRAGMENT)
            {
                currentFragment=-1;
                super.onBackPressed();
            }
            else
            {
                if(showCart)
                {
                    showCart = false;
                    finish();
                }
                else {
                    invalidateOptionsMenu();
                    SetFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment==HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
        return  super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

           if(id== R.id.main_search_icon)
           {
               // seach ở đây
               Intent intent = new Intent(getApplicationContext(),SearchProductActivity.class);
               startActivity(intent);
               return true;
           }else if(id == R.id.main_notification_icon)
           {
               // Thông báo chỗ này
               return true;
           }
           else if(id==R.id.main_cart_icon)
           {
               // xem giỏ hàng chỗ này
               GotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
               return true;
           }
           else if(id == android.R.id.home){
               if(showCart){
                   showCart = false;
                   finish();
                   return true;
               }
           }
        return super.onOptionsItemSelected(item);
    }

    private void GotoFragment(String title, Fragment fragment, int fragmentNo) { // chuyển tới fragment nào
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        SetFragment(fragment,fragmentNo);
        if(fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(2).setChecked(true);
        }
    }

    private void SetFragment(Fragment fragment, int fragmentNo){
        if(fragmentNo!=currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void Init(){
        frameLayout = findViewById(R.id.main_framelayout);
    }

    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginForm.class));
        finish();
    }
}