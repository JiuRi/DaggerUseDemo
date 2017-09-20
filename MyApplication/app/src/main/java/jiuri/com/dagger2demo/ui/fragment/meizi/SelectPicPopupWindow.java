package jiuri.com.dagger2demo.ui.fragment.meizi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import jiuri.com.dagger2demo.R;


public class SelectPicPopupWindow extends PopupWindow{
	private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View mMenuView;
    private Activity mActivity;
	public SelectPicPopupWindow(Activity context,View.OnClickListener itemsOnClick) {
		super(context);
        mActivity=context;
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.activity_select_pic_popup_window, null);
		btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation_AppCompat_Dialog);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(0.5f);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
	}

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(1f);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
      lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }
}
