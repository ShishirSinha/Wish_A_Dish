package com.example.wishadish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.ui.Bills.CurrentOrdersFragment;
import com.example.wishadish.ui.Bills.TotalBillsFragment;
import com.example.wishadish.ui.HumanResources.AddEmployeeFragment;
import com.example.wishadish.ui.HumanResources.ViewEmployeesFragment;
import com.example.wishadish.ui.Reports.ReportsFragment;
import com.example.wishadish.ui.Settings.SelectPrinterFrag;
import com.example.wishadish.ui.Settings.SettingsFragment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wishadish.ui.Waitlist.WaitlistFragment;
import com.example.wishadish.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static com.example.wishadish.LoginSessionManager.EMP_ID;
import static com.example.wishadish.LoginSessionManager.EMP_TOKEN;
import static com.example.wishadish.LoginSessionManager.PREF_NAME;
import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;

public class MainActivity extends AppCompatActivity implements SelectPrinterFrag.sendPrinterNameInterface {

    private final String TAG = getClass().getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    private Menu menu;
    private RelativeLayout homeRl,billRl,currentOrderRl,totalBillsRl,humanResourcesRl,addEmpRl,viewEmpRl,attendanceRl,waitlistRl,settingsRl,reportsRl,logoutRl;
    private TextView homeTv,billsTv,currentOrderTv,totalBillsTv,humanResourcesTv,addEmpTv,viewEmpTv,attendanceTv,waitlistTv,settingsTv,reportsTv,logoutTv;
    private ImageView billsIv,humanResourcesIv,waitlistIv,settingsIv,reportsIv,logoutIv;

    private SettingsFragment shareFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(navigationView);

        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        drawer.addDrawerListener(drawerToggle);

        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, fragment).addToBackStack("homeFrag").commit();

        setTitle("Home");

        menu = navigationView.getMenu();

        // Home
        homeRl = (RelativeLayout) menu.findItem(R.id.nav_home).getActionView().findViewById(R.id.menuHRl);
        homeTv = (TextView) menu.findItem(R.id.nav_home).getActionView().findViewById(R.id.menuHTv);

        // Bills
        billRl = (RelativeLayout) menu.findItem(R.id.nav_bills).getActionView().findViewById(R.id.menuRl);
        billsTv = (TextView) menu.findItem(R.id.nav_bills).getActionView().findViewById(R.id.menuTv);
        billsIv = (ImageView) menu.findItem(R.id.nav_bills).getActionView().findViewById(R.id.menuIv);

        //Current Orders
        currentOrderRl = (RelativeLayout) menu.findItem(R.id.nav_slideshow).getActionView().findViewById(R.id.menuCFRl);
        currentOrderTv = (TextView) menu.findItem(R.id.nav_slideshow).getActionView().findViewById(R.id.menuCFTv);

        // Total Bills
        totalBillsRl = (RelativeLayout) menu.findItem(R.id.nav_tools).getActionView().findViewById(R.id.menuCLRl);
        totalBillsTv = (TextView) menu.findItem(R.id.nav_tools).getActionView().findViewById(R.id.menuCLTv);

        // Human Resources
        humanResourcesRl = (RelativeLayout) menu.findItem(R.id.nav_human_resources).getActionView().findViewById(R.id.menuRl);
        humanResourcesTv = (TextView) menu.findItem(R.id.nav_human_resources).getActionView().findViewById(R.id.menuTv);
        humanResourcesIv = (ImageView) menu.findItem(R.id.nav_human_resources).getActionView().findViewById(R.id.menuIv);

        // Add Employee
        addEmpRl = (RelativeLayout) menu.findItem(R.id.nav_add_employee).getActionView().findViewById(R.id.menuCFRl);
        addEmpTv = (TextView) menu.findItem(R.id.nav_add_employee).getActionView().findViewById(R.id.menuCFTv);

        // View Employees
        viewEmpRl = (RelativeLayout) menu.findItem(R.id.nav_send).getActionView().findViewById(R.id.menuCMRl);
        viewEmpTv = (TextView) menu.findItem(R.id.nav_send).getActionView().findViewById(R.id.menuCMTv);

        // Attendance
        attendanceRl = (RelativeLayout) menu.findItem(R.id.nav_attendance).getActionView().findViewById(R.id.menuCLRl);
        attendanceTv = (TextView) menu.findItem(R.id.nav_attendance).getActionView().findViewById(R.id.menuCLTv);

        // Waitlist
        waitlistRl = (RelativeLayout) menu.findItem(R.id.nav_gallery).getActionView().findViewById(R.id.menuRl);
        waitlistTv = (TextView) menu.findItem(R.id.nav_gallery).getActionView().findViewById(R.id.menuTv);
        waitlistIv = (ImageView) menu.findItem(R.id.nav_gallery).getActionView().findViewById(R.id.menuIv);

        // Settings
        settingsRl = (RelativeLayout) menu.findItem(R.id.nav_share).getActionView().findViewById(R.id.menuRl);
        settingsTv = (TextView) menu.findItem(R.id.nav_share).getActionView().findViewById(R.id.menuTv);
        settingsIv = (ImageView) menu.findItem(R.id.nav_share).getActionView().findViewById(R.id.menuIv);

        // Reports
        reportsRl = (RelativeLayout) menu.findItem(R.id.nav_reports).getActionView().findViewById(R.id.menuRl);
        reportsTv = (TextView) menu.findItem(R.id.nav_reports).getActionView().findViewById(R.id.menuTv);
        reportsIv = (ImageView) menu.findItem(R.id.nav_reports).getActionView().findViewById(R.id.menuIv);

        // Log Out
        logoutRl = (RelativeLayout) menu.findItem(R.id.nav_logout).getActionView().findViewById(R.id.menuRl);
        logoutTv = (TextView) menu.findItem(R.id.nav_logout).getActionView().findViewById(R.id.menuTv);
        logoutIv = (ImageView) menu.findItem(R.id.nav_logout).getActionView().findViewById(R.id.menuIv);

        homeTv.setText("Home");
        homeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "Home Clicked");

                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Home");
                drawer.closeDrawers();
                menu.findItem(R.id.nav_human_resources).setVisible(true);
                menu.findItem(R.id.nav_gallery).setVisible(true);
                menu.findItem(R.id.nav_share).setVisible(true);
                menu.findItem(R.id.nav_gallery).setVisible(true);
                menu.findItem(R.id.nav_reports).setVisible(true);
                closeBills();
                closeHumanResources();
            }
        });

        billsTv.setText("Bills");
        billRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "bills Clicked");

                closeHumanResources();

                if(menu.findItem(R.id.nav_slideshow).isVisible()) {
                    closeBills();
                }
                else{
                    billsIv.setImageResource(R.drawable.arrow_drop_up_black_24dp);
                    menu.findItem(R.id.nav_slideshow).setVisible(true);
                    menu.findItem(R.id.nav_tools).setVisible(true);
                }
            }
        });

        currentOrderTv.setText("Current Orders");
        currentOrderRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "Current Orders Clicked");

                Fragment fragment = new CurrentOrdersFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Current Orders");
                drawer.closeDrawers();
                closeBills();
            }
        });

        totalBillsTv.setText("Total Bills");
        totalBillsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "Total Bills Clicked");

                Fragment fragment = new TotalBillsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Total Bills");
                closeBills();
                drawer.closeDrawers();
            }
        });


        humanResourcesTv.setText("Human Resources");
        humanResourcesRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "HR Clicked");

                closeBills();

                if(menu.findItem(R.id.nav_send).isVisible()) {
                    closeHumanResources();
                }
                else{
                    humanResourcesIv.setImageResource(R.drawable.arrow_drop_up_black_24dp);
                    menu.findItem(R.id.nav_add_employee).setVisible(true);
                    menu.findItem(R.id.nav_send).setVisible(true);
                    menu.findItem(R.id.nav_attendance).setVisible(true);
                }
            }
        });

        addEmpTv.setText("Add Employee");
        addEmpRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddEmployeeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Add Employee");
                drawer.closeDrawers();
                closeHumanResources();
            }
        });

        viewEmpTv.setText("View Employees");
        viewEmpRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewEmployeesFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("View Employees");
                drawer.closeDrawers();
                closeHumanResources();
            }
        });

        attendanceTv.setText("Attendance");
        attendanceRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Yet not prepared!",Toast.LENGTH_SHORT).show();

//                Fragment fragment = new ViewEmployeesFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
//                setTitle("Attendance");
                drawer.closeDrawers();
                closeHumanResources();
            }
        });

        waitlistIv.setVisibility(GONE);
        waitlistTv.setText("Waitlist");
        waitlistRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new WaitlistFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Waitlist");
                drawer.closeDrawers();
                closeBills();
                closeHumanResources();
            }
        });

        settingsIv.setVisibility(GONE);
        settingsTv.setText("Settings");
        settingsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SettingsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Settings");
                drawer.closeDrawers();
                closeBills();
                closeHumanResources();
            }
        });

        reportsIv.setVisibility(GONE);
        reportsTv.setText("Reports");
        reportsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReportsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
                setTitle("Reports");
                drawer.closeDrawers();
                closeBills();
                closeHumanResources();
            }
        });

        logoutIv.setVisibility(GONE);
        logoutTv.setText("Log Out");
        logoutRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Logged Out successfully!",Toast.LENGTH_SHORT).show();

                closeBills();
                closeHumanResources();

                new LoginSessionManager(MainActivity.this).logout();
                finish();
            }
        });




        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_reports)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_gallery:
                fragmentClass = WaitlistFragment.class;
                break;
            case R.id.nav_slideshow:
                fragmentClass = CurrentOrdersFragment.class;
                break;
            case R.id.nav_reports:
                fragmentClass = ReportsFragment.class;
                break;
            case R.id.nav_send:
                fragmentClass = ViewEmployeesFragment.class;
                break;
            case R.id.nav_share:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_tools:
                fragmentClass = TotalBillsFragment.class;
                break;
            case R.id.nav_add_employee:
                fragmentClass = AddEmployeeFragment.class;
                break;
            default:fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.flcontent);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void sendPrinterName(String printer) {
        shareFragment.setPrinterName(printer);
    }

    @Override
    public void onBackPressed() {
        Log.e("Main Activity","Number = "+getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    private void closeBills() {
        billsIv.setImageResource(R.drawable.arrow_drop_down_black_24dp);
        menu.findItem(R.id.nav_slideshow).setVisible(false);
        menu.findItem(R.id.nav_tools).setVisible(false);
    }

    private void closeHumanResources() {
        humanResourcesIv.setImageResource(R.drawable.arrow_drop_down_black_24dp);
        menu.findItem(R.id.nav_add_employee).setVisible(false);
        menu.findItem(R.id.nav_send).setVisible(false);
        menu.findItem(R.id.nav_attendance).setVisible(false);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


//        final FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.flcontent, fragment);
//        // 1. Know how many fragments there are in the stack
//        final int count = fragmentManager.getBackStackEntryCount();
//        // 2. If the fragment is **not** "home type", save it to the stack
//        if( !name.equals("Home") ) {
//            fragmentTransaction.addToBackStack(name);
//        }
//        // Commit !
//        fragmentTransaction.commit();
//        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
//        // OnBackStackChanged callback
//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                // If the stack decreases it means I clicked the back button
//                if( fragmentManager.getBackStackEntryCount() <= count){
//                    // pop all the fragment and remove the listener
//                    fragmentManager.popBackStack(name, POP_BACK_STACK_INCLUSIVE);
//                    fragmentManager.removeOnBackStackChangedListener(this);
//                    // set the home button selected
////                    navigation.getMenu().getItem(0).setChecked(true);
//                    setTitle("abc");
//                }
//            }
//        });
//    }
}
