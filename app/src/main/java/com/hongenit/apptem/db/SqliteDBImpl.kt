package com.hongenit.apptem.db

import android.content.ContentValues
import android.content.Context
import com.hongenit.apptem.classify.ClassifyTypeBean
import com.hongenit.apptem.util.LogUtil

class SqliteDBImpl(context: Context) : ISqliteDB {
    val mDbHelper: DatabaseOpenHelper = DatabaseOpenHelper(context)
    val TAG = "SqliteDBImpl"

    override fun insertChannel(channelBean: ClassifyTypeBean) {
        val database = mDbHelper.writableDatabase
        database.beginTransaction()
        try {
            val contentValues = ContentValues()
            contentValues.put(DbSettings.ChannelTable.CHANNEL_ID, channelBean.channelId)
            contentValues.put(DbSettings.ChannelTable.CHANNEL_NAME, channelBean.channelName)
            contentValues.put(DbSettings.ChannelTable.CHOOSEN_TIME, channelBean.choosenTime)
            database.insert(DbSettings.ChannelTable.TABLE_NAME, null, contentValues)
            database.setTransactionSuccessful()
            LogUtil.i(TAG, "insert success channel = " + channelBean)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            database.endTransaction()
            database.close()
        }
    }

    override fun queryAllChannel(): ArrayList<ClassifyTypeBean> {
        val list = arrayListOf<ClassifyTypeBean>()
        val db = mDbHelper.writableDatabase
        val cursor = db.query(DbSettings.ChannelTable.TABLE_NAME, null, null, null, null, null, DbSettings.ChannelTable.CHOOSEN_TIME + " DESC")
        try {
            if (cursor == null) {
                return list
            }
            val channelIdIndex = cursor.getColumnIndex(DbSettings.ChannelTable.CHANNEL_ID)
            val channelNameIndex = cursor.getColumnIndex(DbSettings.ChannelTable.CHANNEL_NAME)
            val choosenTimeIndex = cursor.getColumnIndex(DbSettings.ChannelTable.CHOOSEN_TIME)
            while (cursor.moveToNext()) {
                val channelId = cursor.getString(channelIdIndex)
                val channelName = cursor.getString(channelNameIndex)
                val choosenTime = cursor.getString(choosenTimeIndex)
                list.add(ClassifyTypeBean(channelId, channelName, choosenTime.toLong()))
            }
        } finally {
            if (cursor != null) {
                cursor.close()
            }
            db.close()
        }
        return list
    }


}