 package com.jj.investigation.customebehavior.activity;

 import android.content.Intent;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.view.View;

 import com.jj.investigation.customebehavior.R;

 public class CustomeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_view);
    }

     public void verticalScrollView(View view) {
         startActivity(new Intent(this, VerticalScrollViewActivity.class));
     }

     public void slideMenu(View view) {
         startActivity(new Intent(this, SlideMenuActivity.class));
     }


}
