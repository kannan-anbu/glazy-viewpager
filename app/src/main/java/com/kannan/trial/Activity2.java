package com.kannan.trial;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
        getWindow().setSharedElementExitTransition(new ChangeImageTransform());

        setContentView(R.layout.activity_2);

        final Button b = (Button) findViewById(R.id.bt_close);
        final TextView tv = (TextView) findViewById(R.id.tv);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(Activity2.this, tv, "tr1");
                startActivity(intent, options.toBundle());

            }
        });
    }
}
