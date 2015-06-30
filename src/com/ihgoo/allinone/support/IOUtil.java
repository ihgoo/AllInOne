package com.ihgoo.allinone.support;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class IOUtil {
	
	
	   private IOUtil() {
	    }

	    public static void closeQuietly(Closeable closeable) {
	        if (closeable != null) {
	            try {
	                closeable.close();
	            } catch (Throwable e) {
	            }
	        }
	    }

	    public static void closeQuietly(Cursor cursor) {
	        if (cursor != null) {
	            try {
	                cursor.close();
	            } catch (Throwable e) {
	            }
	        }
	    }

    /**
     * @return 导出数据流
     */
    public static BufferedInputStream exportData(Context context,String dbName) {
        try {
            // 当前程序路径
            String path = context.getFilesDir().getAbsolutePath();
            path = path + "/../databases/" + dbName;
            File file = new File(path);
            FileInputStream inStream = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(inStream);
            return in;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * @param in 导入数据流
     */
    public static void importData(Context context,BufferedInputStream in, String dbName) {
        try {
            // 当前程序路径
            String path = context.getFilesDir().getAbsolutePath();
            path = path + "/../databases/";
            File file = new File(path);
            file.mkdirs();
            path += dbName;
            file = new File(path);
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            BufferedOutputStream out = new BufferedOutputStream(outStream);
            int c;
            while ((c = in.read()) >= 0) {
                out.write(c);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String toString(InputStream inputStream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        try {
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }
}
