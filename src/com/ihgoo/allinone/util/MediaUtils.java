package com.ihgoo.allinone.util;

import java.io.File;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class MediaUtils {

	/**
	 * 向多媒体数据库手动添加(Android4.4中拒绝发送Intent.ACTION_MEDIA_MOUNTED扫描SD卡的广播)
	 * 
	 * @param mContext
	 * @param filePath
	 */
	public static void insertMediaFile(final Context mContext, String filePath) {
		if (filePath == null) {
			return;
		}
		// broadcast to scan file
		boolean bmnt = Environment.getExternalStorageDirectory().getPath()
				.startsWith("/mnt");

		File file = new File(bmnt ? ("/mnt" + filePath) : filePath);

		MediaScannerConnection.scanFile(mContext,
				new String[] { file.toString() }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					@Override
					public void onScanCompleted(String path, Uri uri) {

						ContentResolver cr = mContext.getContentResolver();
						long datemodified = 0;
						long dateadded = 0;
						Cursor cursor = cr.query(uri, null, null, null, null);
						if (cursor != null && cursor.moveToFirst()) {
							datemodified = cursor.getLong(cursor
									.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED));
							dateadded = cursor.getLong(cursor
									.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
							cursor.close();
						}

						ContentValues values = new ContentValues();
						if (datemodified > 0
								&& String.valueOf(datemodified).length() > 10) {
							values.put(MediaStore.MediaColumns.DATE_MODIFIED,
									datemodified / 1000);
						}
						if (dateadded > 0
								&& String.valueOf(dateadded).length() > 13) {
							values.put(MediaStore.MediaColumns.DATE_ADDED,
									dateadded / 1000);
						}

						if (values.size() > 0) {
							cr.update(uri, values, null, null);
						}
						
					}
				});
	}

	/**
	 * 手动单独扫描相册文件
	 * 
	 * @param context
	 *            上下文
	 * @param filename
	 *            文件完整路径
	 */
	public static void updateGallery(final Context context, String filename) {
		MediaScannerConnection.scanFile(context, new String[] { filename },
				null, new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {
						context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
						LogUtils.i("Scanned " + path + ":");
						LogUtils.i("-> uri=" + uri);
					}
				});
	}
	
	
	public static void fileScan(final Context context, String file){  
        Uri data = Uri.parse("file://"+file);  
        LogUtils.i("file:"+file);  
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));  
    }  
      
    public static void folderScan(final Context context, String path){  
        File file = new File(path);  
          
        if(file.isDirectory()){  
            File[] array = file.listFiles();  
              
            for(int i=0;i<array.length;i++){  
                File f = array[i];  
                  
                if(f.isFile()){//FILE TYPE  
                    String name = f.getName();  
                      
                    if(name.contains(".jpg")){  
                        fileScan(context,f.getAbsolutePath());  
                    }  
                }  
                else {//FOLDER TYPE  
                    folderScan(context,f.getAbsolutePath());  
                }  
            }  
        }  
    }  
	
	
	
	
	
}
