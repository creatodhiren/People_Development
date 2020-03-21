package com.creatoweb.peopledevelopment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavOptions;

import com.creatoweb.peopledevelopment.agent.activity.login.Login;
import com.creatoweb.peopledevelopment.agent.fragment.profile.ProfileModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.utils.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.navigation.Navigation.findNavController;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public static final String TAG = "Navigation";
    private TextView userName;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.bnav_payment:
                setBottomFragment(R.id.paymentFragment);
                return true;
            case R.id.bnav_registration:
                setBottomFragment(R.id.registrationFragment);
                return true;
            case R.id.bnav_dashboard:
                setBottomFragment(R.id.dashboard);
                return true;
            case R.id.bnav_paymentlist:
                setBottomFragment(R.id.paymentListFragment);
                return true;
        }
        return false;
    };

    void setBottomFragment(int id) {
        if (id == R.id.bnav_payment)
            findNavController(Navigation.this, R.id.my_nav_host_fragment).popBackStack(id, false);
        else findNavController(Navigation.this, R.id.my_nav_host_fragment).navigate(id,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                        .build());

    }


    void setNavFragment(int id) {
        new Handler().postDelayed(() -> {
            if (id == R.id.bnav_payment)
                findNavController(Navigation.this, R.id.my_nav_host_fragment).popBackStack(id, false);
            else findNavController(Navigation.this, R.id.my_nav_host_fragment).navigate(id,
                    null,
                    new NavOptions.Builder()
                            .setEnterAnim(R.anim.nav_default_enter_anim)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                            .build());
        }, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHeader(navigationView.getHeaderView(0));
        View hView = navigationView.getHeaderView(0);
        userName = hView.findViewById(R.id.name);
        getUserDetails();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        findNavController(Navigation.this, R.id.my_nav_host_fragment).addOnDestinationChangedListener((controller, destination, arguments) -> {
            ((TextView) (findViewById(R.id.toolbar_title))).setText(destination.getLabel());
            if (destination.getLabel().toString().equalsIgnoreCase("Payment"))
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
            else if (destination.getLabel().toString().equalsIgnoreCase("Registration"))
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            else if (destination.getLabel().toString().equalsIgnoreCase("Dashboard")) {
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
            } else if (destination.getLabel().toString().equalsIgnoreCase("Payment List"))
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
        });
    }



    void setHeader(View view) {
        TextView name = view.findViewById(R.id.name);
        LinearLayout profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(view1 -> {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            setNavFragment(R.id.profile2);
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle toggleDrawer view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_saving:
                setNavFragment(R.id.savingFragment);
                break;
            case R.id.nav_fd:
                setNavFragment(R.id.FDFragment);
                break;
            case R.id.nav_rd:
                setNavFragment(R.id.RDFragment);
                break;
            case R.id.nav_dds:
                setNavFragment(R.id.DDSFragment);
                break;
            case R.id.nav_mis:
                setNavFragment(R.id.MISFragment);
                break;
            case R.id.nav_myaccount:
                setNavFragment(R.id.myaccountFragment);
                break;
            case R.id.help:
                setNavFragment(R.id.help2);
                break;
            case R.id.share_app:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Student Offer");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
                break;
            case R.id.rate_app:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.logout:
                AppPref appPref = AppPref.getInstance(Navigation.this);
                appPref.edit();
                appPref.clear();
                appPref.commit();
                startActivity(new Intent(Navigation.this, Login.class));
                finish();

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserDetails() {
        ApiClient.getApiClient().getProfile(UserSession.getId(this)).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileModel profileModel = response.body();

                    if (profileModel.getMessage().equalsIgnoreCase("Success!")) {
                        ProfileModel.Data userData = profileModel.getData();
                        userName.setText(userData.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
    }
}
