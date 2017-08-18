package jiuri.com.dagger2demo.dagger2;

import dagger.Component;
import jiuri.com.dagger2demo.MainActivity;

/**
 * Created by user103 on 2017/8/18.
 */
@Component(modules = MainModel.class)
public interface MainComponent {
        void inject(MainActivity mainModel);
}
