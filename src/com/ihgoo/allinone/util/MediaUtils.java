package com.ihgoo.allinone.util;

import java.io.File;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class MediaUtils {

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

	public static void updateGallery(Context context, String filename)// filename是我们的文件全名，包括后缀
	{
		MediaScannerConnection.scanFile(context, new String[] { filename },
				null, new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {
						LogUtils.i("Scanned " + path + ":");
						LogUtils.i("-> uri=" + uri);
					}
				});
	}
}
