package win.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT =0;
    private static final int FAVORITE_FRAGMENT =1;
    private static final int MY_ACCOUNT_FRAGMENT = 2;
    private static int currentFragment;
    private NavigationView navigationView;
    private ImageView noInternetConnection;
    public static DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open ,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_home, R.id.nav_my_favorite, R.id.nav_sign_out, R.id.nav_my_account)
                .setDrawerLayout(drawer)
                .build();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        frameLayout = findViewById(R.id.main_framelayout);
        noInternetConnection = findViewById(R.id.no_internet_connection);




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if(currentFragment == HOME_FRAGMENT) {
                super.onBackPressed();
            }else{
                invalidateOptionsMenu();
                setFragment(new HomeFragment(),HOME_FRAGMENT);
                getSupportActionBar().setTitle(null);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id== R.id.main_search_icon){
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }else if(id==R.id.main_favorite_icon){
            gotoFragment("My Favorite",new MyWishListFragment(),FAVORITE_FRAGMENT);
            return  true;
        }else if(id==R.id.main_notification_icon){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo == FAVORITE_FRAGMENT) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
    }
    public void setFragment(Fragment fragment, int fragmentNo){
        currentFragment = fragmentNo;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            int id = menuItem.getItemId();
            if (id == R.id.nav_my_home) {
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), HOME_FRAGMENT);
            } else if (id == R.id.nav_my_favorite) {
                gotoFragment("My Favorite", new MyWishListFragment(), FAVORITE_FRAGMENT);
            } else if (id == R.id.nav_my_account) {
                gotoFragment("My Account", new MyAccountFragment(), MY_ACCOUNT_FRAGMENT);
            } else if (id == R.id.nav_sign_out) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent loginIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(loginIntent);
                finish();
            }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
