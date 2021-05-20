package com.example.giftsapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.FrameLayout;

import com.example.giftsapp.Controller.Fragment_Accounts.Bill;
import com.example.giftsapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;

    private static final int HOME_FRAGMENT=0;
    private static final  int CART_FRAGMENT=1;
    private static final int ORDERS_FRAGMENT=2;
    private  static final int ACCOUNT_FRAGMENT=3;

    private FrameLayout frameLayout;
    private static int currentFragment =-1;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        frameLayout = findViewById(R.id.main_framelayout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_order:
<<<<<<< HEAD
=======
                        Intent intent = new Intent(getApplicationContext(), SettingAccountForm.class);
                        intent.putExtra("EXTRA_DOCUMENT_OPEN_BILL", true);
                        startActivity(intent);
                        finish();
                        //GotoFragment("My Order",new MyOrdersFragment(),ORDERS_FRAGMENT);
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
                        break;
                    case R.id.nav_discount:
                        break;
                    case R.id.nav_account:
<<<<<<< HEAD
=======
                        //gotoFragment("My Account",new MyAccountFragment(),ACCOUNT_FRAGMENT);
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
                        startActivity(new Intent(getApplicationContext(), SettingAccountForm.class));
                        finish();
                        break;
                    case R.id.nav_cart:
<<<<<<< HEAD
                        MyCart();
=======
                        GotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
                        break;
                    case R.id.nav_logout:
                        LogOut();
                        break;
<<<<<<< HEAD
=======
                    case R.id.nav_home:
                        invalidateOptionsMenu();
                        SetFragment(new HomeFragment(),HOME_FRAGMENT);
                        break;
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
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
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

        SetFragment(new HomeFragment(),HOME_FRAGMENT);
<<<<<<< HEAD
=======
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
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
<<<<<<< HEAD

        if(id==R.id.main_search_icon)
        {
            // search chỗ này
            return true;
        }
        else if(id == R.id.main_notification_icon)
        {
            // Thông báo chỗ này
            return true;
        }
        else if(id == R.id.main_cart_icon)
        {
            // xem giỏ hàng chỗ này
            MyCart();
            return true;
=======
        switch (id) {
            case R.id.main_search_icon:
                // search chỗ này
                break;
            case R.id.main_notification_icon:
                // Thông báo chỗ này
                break;
            case R.id.main_cart_icon:
                // xem giỏ hàng chỗ này
                GotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
                break;
            default:
                break;
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
        }
        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD
    private void MyCart() {
        invalidateOptionsMenu();
        SetFragment(new MyCartFragment(),CART_FRAGMENT);
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    private void SetFragment(Fragment fragment, int fragmentNo){
        currentFragment = fragmentNo;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
=======
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
>>>>>>> 2e40bb4f501e654a2b80c61d0fca506d22e3a3b7
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