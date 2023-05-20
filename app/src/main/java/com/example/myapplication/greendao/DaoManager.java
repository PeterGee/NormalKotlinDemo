package com.example.myapplication.greendao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.greendao.entity.DaoMaster;
import com.example.myapplication.greendao.entity.DaoSession;
import com.example.myapplication.greendao.entity.Note;
import com.example.myapplication.greendao.entity.NoteDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author qipeng
 * @Date 2023/5/20
 * @Desc
 */
public class DaoManager {
    private final static String dbName = "note.db";
    private static DaoManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private DaoSession sDaoSession;
    private static DaoMaster sDaoMaster;

    public DaoManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DaoManager.class) {
                if (mInstance == null) {
                    mInstance = new DaoManager(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 判断是否有存在数据库，如果没有则创建
     * @return
     */
    public DaoMaster getDaoMaster(String DBName){
        if(sDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DBName, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }
    /**
     * 插入dirinfo一条记录
     *
     * @param
     */
    public void insertDir(Note dir) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        sDaoSession = daoMaster.newSession();
        NoteDao dirDao = sDaoSession.getNoteDao();
        dirDao.insertOrReplace(dir);
    }

    /**
     * 查询用户列表
     */
    public List<Note> queryList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NoteDao dirDao = daoSession.getNoteDao();
        QueryBuilder<Note> qb = dirDao.queryBuilder();
        List<Note> list = qb.list();
        return list;
    }

    public List<Note> queryList(Long id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NoteDao dirDao = daoSession.getNoteDao();
        QueryBuilder<Note> qb = dirDao.queryBuilder();
        qb.where(NoteDao.Properties.Id.gt(id)).orderAsc(NoteDao.Properties.Id);
        List<Note> list = qb.list();
        return list;
    }
    /**
     * 更新一条记录
     *
     * @param dir
     */
    public void updateItem(Note dir) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NoteDao dirDao = daoSession.getNoteDao();
        dirDao.update(dir);
    }
    /**
     * 删除一条记录
     *
     * @param dir
     */
    public void deleteItem(Note dir) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NoteDao dirDao = daoSession.getNoteDao();
        dirDao.delete(dir);
    }
    /**
     * 删除所有数据
     *
     * @param
     */
    public void deleteAllDir() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NoteDao dirDao = daoSession.getNoteDao();
        dirDao.deleteAll();
    }
    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    private void closeHelper(){
        if(openHelper != null){
            openHelper.close();
            openHelper = null;
        }
    }
    private void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

}