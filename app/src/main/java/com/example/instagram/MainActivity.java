package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ImageView iconImage;
    private LinearLayout linearLayout;
    private Button register, logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconImage = findViewById(R.id.icon_image);
        linearLayout = findViewById(R.id.linear_layout);
        register = findViewById(R.id.register);
        logIn = findViewById(R.id.login);



        linearLayout.animate().alpha(0f).setDuration(1);    //works as invisibility of linear layout before Insta icon animation

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1500);
        animation.setDuration(1500);
        animation.setFillAfter(false);
        animation.setAnimationListener( new MyAnimationListener());

        iconImage.setAnimation(animation);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent(MainActivity.this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                //For more info on CLEAR_TASK & CLEAR_TOP, refer below two links
                // https://stackoverflow.com/questions/21833402/difference-between-intent-flag-activity-clear-task-and-intent-flag-activity-task
                // https://developer.android.com/reference/android/content/Intent#FLAG_ACTIVITY_CLEAR_TOP

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            iconImage.clearAnimation();
            iconImage.setVisibility(View.INVISIBLE);
            linearLayout.animate().alpha(1f).setDuration(1000);  //works as visibility of linear layout after Insta icon animation
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            startActivity( new Intent(MainActivity.this, MainActivity2.class));
            finish();
        }
    }
}