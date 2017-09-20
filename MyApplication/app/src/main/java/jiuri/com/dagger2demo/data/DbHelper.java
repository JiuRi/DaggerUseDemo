package jiuri.com.dagger2demo.data;

import android.content.Context;

import com.mydb.db.DaoMaster;
import com.mydb.db.DaoSession;
import com.mydb.db.DateInfoDao;

import java.util.List;

import jiuri.com.dagger2demo.ui.fragment.riji.DateInfo;

/**
 * Created by user103 on 2017/9/20.
 */

public class DbHelper  {
    private Context mContext;
    private DateInfoDao mDateInfoDao;

    public DbHelper(Context context) {
        this.mContext=context;
        initDb();
    }

    private void initDb() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "mydb", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mDateInfoDao = daoSession.getDateInfoDao();
    }
    //增加
    public void addRiJi(DateInfo info){
        mDateInfoDao.insert(info);
    }
    //删除
    public void deleteRiJi(DateInfo info){
        mDateInfoDao.delete(info);
    }
    //改变
    public void updateRiJi(DateInfo info){
        mDateInfoDao.update(info);
    }
    //查询全部
    public List<DateInfo> queryRiJiAll(){
        List<DateInfo> list = mDateInfoDao.queryBuilder().build().list();
        return list;
    }
}
