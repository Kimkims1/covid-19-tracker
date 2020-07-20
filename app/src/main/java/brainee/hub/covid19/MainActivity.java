package brainee.hub.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView titleTv;
    private BottomNavigationView bottomNavigationView;
    private ImageButton refresh;

    private Fragment homeFragment, statFragment;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTv = findViewById(R.id.titleTv);
        bottomNavigationView = findViewById(R.id.navigationView);
        refresh = findViewById(R.id.refreshBtn);

        initFragments();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        statFragment = new StatsFragment();

        fragmentManager = getSupportFragmentManager();
        activeFragment = homeFragment;

        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, homeFragment, "homeFragment")
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, statFragment, "statsFragment")
                .hide(statFragment)
                .commit();
    }

    private void loadHomeFragment() {
        titleTv.setText("Home");
        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
        activeFragment = homeFragment;
    }

    private void loadStatsFragment() {
        titleTv.setText("COVID 19 Stats");
        fragmentManager.beginTransaction().hide(activeFragment).show(statFragment).commit();
        activeFragment = statFragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                loadHomeFragment();
                return true;
            case R.id.nav_stats:
                loadStatsFragment();
                return true;
        }
        return false;
    }
}