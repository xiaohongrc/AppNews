package com.hongenit.apptem.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Xiaohong on 2018/5/10.
 * desc:
 */
class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "channels.db"
        val DB_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {
        createFavouritesTable(db)
    }


    // 创建喜欢图片的表格
    private fun createFavouritesTable(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE " + DbSettings.ChannelTable.TABLE_NAME + "(" +
                DbSettings.ChannelTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbSettings.ChannelTable.CHANNEL_ID + " TEXT," +
                DbSettings.ChannelTable.CHANNEL_NAME + " TEXT," +
                DbSettings.ChannelTable.CHOOSEN_TIME + " TEXT" +
                ")"

        db?.execSQL(sql)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }
}