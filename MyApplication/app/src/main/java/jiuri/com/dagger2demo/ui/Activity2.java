package jiuri.com.dagger2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/8/25.
 */

public class Activity2 extends AppCompatActivity {
    private MyView mChecke;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        mChecke = (MyView) findViewById(R.id.checke);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChecke.check();
            }
        });

    }
}
