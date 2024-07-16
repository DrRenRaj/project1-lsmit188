package edu.msudenver.cs3013.project1_s24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.*;
import com.google.android.material.navigation.NavigationView;

class MainActivity2 : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setSupportActionBar(findViewById(R.id.toolbar));
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment;
        val navController = navHostFragment.navController;
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ),
            findViewById(R.id.drawer_layout));
        setupActionBarWithNavController(navController, appBarConfiguration);
        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController);
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment);
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp();
    }
}
