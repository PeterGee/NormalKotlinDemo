package com.example.myapplication.room.model

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @date 2021/4/14
 * @author qipeng
 * @desc userDao
 */
@Dao
interface UserDao {
   /* *//**
     * 查询全部
     *//*
    @Query("SELECT *FROM users")
    fun getAll(): List<User>

    *//**
     * 查询userIds中user
     *//*
    @Query("SELECT * FROM users WHERE uid IN(:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    *//**
     * 模糊查询
     *//*
    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    *//**
     * 插入数据
     *//*
    @Insert
    fun insertAll(vararg users: User)

    *//**
     * 指定插入冲突策略
     *//*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    *//**
     * 删除数据
     *//*
    @Delete
    fun delete(user: User)

    *//**
     * 更新user操作
     *//*
    @Update
    fun updateUser(vararg users: User)

    *//**
     * 年龄查询大于minAge的数据
     *//*
    @Query("SELECT * FROM users WHERE age> :minAge")
    fun loadAllUserOlderThan(minAge: Int): List<User>

    *//**
     * 查询区间值user
     *//*
    @Query("SELECT *FROM users WHERE age BETWEEN :minAge AND :maxAe")
    fun loadUsersBetweenAge(minAge: Int, maxAe: Int): List<User>

    *//**
     * 查询全名，返回新的model
     *//*
    @Query("SELECT first_name,last_name FROM USERS")
    fun loadFullName(): List<NameTuple>

    *//**
     * 查询传递参数的集合
     *//*
    @Query("SELECT first_name,last_name FROM users WHERE region IN (:regions)")
    fun loadUsersFromRegions(regions: List<String>): List<NameTuple>

    *//**
     * 查询年龄大于最小年龄的Cursor，限制5条数据
     *//*
    @Query("SELECT * FROM users WHERE age>:minAge LIMIT 5")
    fun loadRawUserOlderThan(minAge: Int): Cursor

 *//*   *//**//**
     * 多表查询
     *//**//*
    @Query("SELECT * FROM Book " +
            "INNER JOIN loan ON loan.book_id = book.id " +
            "INNER JOIN user ON user.id = loan.user_id " +
            "WHERE user.name LIKE :userName")*//*
    fun loadDataFromMulityTable(userName: String): List<Book>

    *//**
     * 查询user数据为UserPet赋值，并返回UserPet
     *//*
    @Query("SELECT first_name AS userName,last_name AS petName FROM users WHERE age> 10")
    fun loadUserAndPetNames(): LiveData<List<UserPet>>

    data class UserPet(val userName: String?, val petName: String?)

    *//**
     * 通过协程插入数据
     *//*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserByCoroutine(vararg users: User)

    *//**
     * 通过协程更新数据
     *//*
    @Update
    suspend fun updateUserByCoroutine(vararg user: User)

    *//**
     * 通过协程删除数据
     *//*
    @Delete
    suspend fun deleteUserByCoroutine(vararg users: User)

    *//**
     * 通过协程查询所有数据
     *//*
    @Query("SELECT * FROM users")
    suspend fun loadAllUsersByCoroutine(): Array<User>

    @Transaction // 设置在单个数据库事务中运行 保持原子性操作
    suspend fun setLoggedInUser(loggedUser:User){
        deleteUserByCoroutine(loggedUser)
        insertUserByCoroutine(loggedUser)
    }

 *//*   *//**//**
     * 创建视图
     *//**//*
    @DatabaseView("SELECT users.uid,users.firstName,users.lastName,"+
            "department.name AS departmentName FROM user " +
            "INNER JOIN department ON user.departmentId = department.id")*//*
    data class UserDetail(
        val id:Long,
        val name:String?,
        val departmentId:Long,
        val departmentName:String?
    )

    *//**
     * 将视图与数据库相关联
     *//*
    @Database(entities = [User::class],views = [UserDetail::class],version = 1)
    abstract class AppDatabase:RoomDatabase(){
        abstract fun userDao():UserDao
    }
*/
}