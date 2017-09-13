package jiuri.com.dagger2demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.util.ToastUtil;

/**
 * Created by user103 on 2017/9/11.
 */

public class MainActivity2 extends AppCompatActivity {
    @BindView(R.id.editext)
    EditText mEditext;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.regist)
    Button mRegist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.login, R.id.regist})
    public void onViewClicked(View view) {
        Editable text = mEditext.getText();
        if (TextUtils.isEmpty(text)){
            ToastUtil.showToast("请先输入您的ID");
            return;
        }
        else {
            switch (view.getId()) {
                case R.id.login:
                    Intent intent=new Intent(this,LoginActivity.class);
                   // intent.putExtra("type",0);
                    intent.putExtra("idd",text);
                    startActivityForResult(intent,100);
                    break;
                case R.id.regist:
                    Intent intent1=new Intent(this,LoginActivity.class);
                  //  intent1.putExtra("type",1);
                    intent1.putExtra("idd",text);
                    startActivityForResult(intent1,101);
                    break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if (requestCode==100){
                    ToastUtil.showToast("登陆成功");
                }

                break;
            case 101:
                if (requestCode==101){
                    ToastUtil.showToast("注册成功");
                }
                break;
        }
    }
}
