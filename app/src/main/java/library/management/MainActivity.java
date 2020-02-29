package library.management;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import library.management.Class.User;
import library.management.Controller.StoreData;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private StoreData controller;
    private User user;
    private TextView UserName, UserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        UserName = (TextView)hView.findViewById(R.id.tvUserName);
        UserEmail = (TextView)hView.findViewById(R.id.tvUserEmail);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Search_Book, R.id.nav_History, R.id.nav_New_Book,
                R.id.nav_Book_Issue, R.id.nav_Book_Loss, R.id.nav_Book_Return, R.id.nav_New)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        controller = new StoreData(this);
        user = controller.getCurrentUser();
        UserName.setText(user.getName());
        UserEmail.setText(user.getEmail());
        if (!controller.isAdmin())
            navigationView.getMenu().findItem(R.id.nav_Admin).setVisible(false);
        else
            navigationView.getMenu().findItem(R.id.nav_Admin).setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btnLogout)
            logout();
        return false;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void logout(){
        controller.logoutUser();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
