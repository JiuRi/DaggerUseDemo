package jiuri.com.dagger2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/8/25.
 */

public class Activity2 extends AppCompatActivity {
    private MyView mChecke;
    private Button mButton;
    private SeekBar mSeekbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initView();
        initListener();
    }

    private void initListener() {
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        mChecke = (MyView) findViewById(R.id.checke);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
    }
}
