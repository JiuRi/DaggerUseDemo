package jiuri.com.dagger2demo.dagger2;

import dagger.Module;
import dagger.Provides;
import jiuri.com.dagger2demo.MainActivity;

/**
 * 这里对dagger2 的使用是鉴于 MainActivity 和 implMainPresent 的
 * 因而 首先我应当想到的是 我们的 Mainactivity 需要 ImplMainPresent 这个 对象 所以第一步的话 我么首先是在MainActivity 当中
 * 通过 Inject ImplMianPresent mImplMainPresent ;(标注我们需要在这里注入这个类)
 * 第二步 ： 当我们知道 需要注入那个累的时候 我们要相对应的去这个类 的构造方法去标注一下  与之相对应，表示这个类需要注解
 * 的时候会被 注入进去；（注入到哪  也就是和第一步 的操作相对应）
 *
 * 第三部  创建一个MainModel类 也就是 下面这个类；我们需要注入进去的类 需要哪些参数 ，@Model   @Provides
 *
 *第四部就是 创建一个 Interface 接口 ，然后 表示我们 需要注入到哪里  也就是 inject 和 modesl 的桥梁；
 * Created by user103 on 2017/8/18.
 * 第五部就是 调用；
 */
@Module
public class MainModel {
    private MainActivity mMainActivity;
    public MainModel(MainActivity mainActivity) {
        this.mMainActivity=mainActivity;
    }
    @Provides
    public MainActivity provideMianView(){
        return mMainActivity;
    }
}
