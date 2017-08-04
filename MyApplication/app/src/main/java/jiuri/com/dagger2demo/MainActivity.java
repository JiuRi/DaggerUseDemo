package jiuri.com.dagger2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiuri.com.dagger2demo.present.impl.ImplMainPresent;

/**
 *
 *
 */
public class MainActivity extends AppCompatActivity {
private  static  String TAG="MainActivity";

    @BindView(R.id.editext)
    EditText mEditext;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.text)
    TextView mText;
    private String mText1;
    private ImplMainPresent mImplMainPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImplMainPresent = new ImplMainPresent(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mText1 = mEditext.getText().toString().trim();
        mImplMainPresent.loadDate(mText1);
    }

    public void setTianqiDetail(String s) {
        mText.setText(s);
    }
}
