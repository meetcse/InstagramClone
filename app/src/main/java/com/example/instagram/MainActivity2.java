package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagram.Fragments.HomeFragment;
import com.example.instagram.Fragments.NotificationFragment;
import com.example.instagram.Fragments.ProfileFragment;
import com.example.instagram.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

      bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              switch (menuItem.getItemId()) {
                  case R.id.nav_home :
                      selectorFragment = new HomeFragment();
                      break;

                  case R.id.nav_search :
                      selectorFragment = new SearchFragment();
                      break;

                  case R.id.nav_add :
                      selectorFragment = null;
                      startActivity( new Intent(MainActivity2.this, PostActivity.class));
                      break;

                  case R.id.nav_heart :
                      selectorFragment = new NotificationFragment();
                      break;

                  case R.id.nav_profile :
                      selectorFragment = new ProfileFragment();
                      break;
              }

              if(selectorFragment != null) {
                  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
              }
              return true;
          }
      });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }
}