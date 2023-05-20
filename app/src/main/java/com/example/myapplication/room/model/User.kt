/*
package com.example.myapplication.room.model

import android.graphics.Bitmap
import androidx.room.*

*/
/**
 * @date 2021/4/14
 * @author qipeng
 * @desc Room entity
 *//*


open class BaseUser(
    var picture: Bitmap? = null
)


// @Fts4(languageId = "lid") // 全文搜索 // 指定用于存储每一行语言信息的列
// 指定表名、指定忽略字段，使用unique指定唯一性属性
@Entity(tableName = "users",
    ignoredColumns = ["picture"],
    indices = [Index(value = ["last_name", "address"],
        unique = true)])
data class User(
    // 主键
    @PrimaryKey val uid: Int,
    // 列名
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "lid") val languageId: Int,
    @ColumnInfo val address: String?,
    @ColumnInfo val age: Int?,
    @ColumnInfo val region: String?
) : BaseUser()

*/
/**
 * 返回tuple元组
 *//*

data class NameTuple(
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)

*/
/**
 * 创建表关联
 *//*

data class UserAndNameTuple(
    @Embedded val user: User,
    // parentColumn设置父实体主键列的名称，并将 entityColumn 设置为引用父实体主键的子实体列的名称
    @Relation(
        parentColumn = "firstName",
        entityColumn = "firstName"
    )
    val nameTuple: NameTuple
)

@Entity
abstract class AbstractUser {
    @PrimaryKey
    abstract fun getId(): Long?
    abstract fun getFirstName(): String?
    abstract fun getLastName(): String?

}

@Entity
data class Book(
    @PrimaryKey @ColumnInfo val bookId:String?,
    @ColumnInfo val bookName:String?,
    @ColumnInfo val bookPage:String?
)

@Entity
data class Playlist(
    @PrimaryKey val playlistId:Long,
    val playlistName:String
)

@Entity
data class Song(
    @PrimaryKey val songId:Int,
    val songName:String,
    val artList:String
)

@Entity(primaryKeys = ["playlistId","songId"])
data class PlaylistSongCrossRef(
    val playlistId:Long,
    val songId:Long
)

// 关联表查询
data class PlaylistWithSongs(
    @Embedded val song:Song,
    @Relation(
        parentColumn = "songId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val playlists:Playlist
)

*/
