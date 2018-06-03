package com.hongenit.apptem.db;

import android.provider.BaseColumns;

/**
 * Created by Xiaohong on 2018/5/10.
 * desc:
 */

public class DbSettings {
    public static class ChannelTable implements BaseColumns {
        public static String TABLE_NAME = "channels";
        public static String CHANNEL_NAME = "channel_name";
        public static String CHANNEL_ID = "channel_id";
        public static String CHOOSEN_TIME = "choosen_time";
    }


}
