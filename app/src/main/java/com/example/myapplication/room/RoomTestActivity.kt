package com.example.myapplication.room

import androidx.appcompat.app.AppCompatActivity

/**
 * @date 2021/4/14
 * @author qipeng
 * @desc Room数据库
 */
class  RoomTestActivity : AppCompatActivity() {
/*
    private var mDb: AppDatabase? = null
    private var mDao: UserDao? = null
    val mUser = User(1, "张", "三", 1, "北京", 28, "未知")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)
        // createDB
        mDb = Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME).build()
        mDao = mDb!!.userDao()
        // 数据库迁移
        // migrationDb()

        initView()
    }*/

    private fun migrationDb() {
    /*    Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME)
            .createFromAsset("database/myapp.db")
            .fallbackToDestructiveMigration()
            .build()

        Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME)
            .addMigrations(object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                            "PRIMARY KEY(`id`))")

                    database.execSQL("""
                CREATE TABLE new_Song (
                    id INTEGER PRIMARY KEY NOT NULL,
                    name TEXT,
                    tag TEXT NOT NULL DEFAULT ''
                )
                """.trimIndent())

                    database.execSQL("""
                INSERT INTO new_Song (id, name, tag)
                SELECT id, name, tag FROM Song
                """.trimIndent())

                    database.execSQL("DROP TABLE Song")

                    database.execSQL("ALTER TABLE new_Song RENAME TO Song")
                }

            })*/

    }

    private fun initView() {
   /*     var mContent = "暂无数据"
        btnAdd.setOnClickListener {
            mDao?.insertUsers(mUser)
        }
        btnDel.setOnClickListener {
            mDao?.delete(mUser)
        }
        btnModify.setOnClickListener {
            mDao?.updateUser(mUser)
        }
        btnSearch.setOnClickListener {
            val list = mDao?.getAll()
            if (!list.isNullOrEmpty()) {
                mContent = "元素的名字是：" + list[0].firstName + list[0].lastName
            }
        }
        tvContent.text = mContent
        */
    }

    companion object {
        const val DB_NAME = "users"
    }
}