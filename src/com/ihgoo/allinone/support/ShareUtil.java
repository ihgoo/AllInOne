package com.ihgoo.allinone.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class ShareUtil {
	/**
	 * 分享文本信息
	 * 
	 * @param title
	 *            分享标题
	 * @param content
	 *            分享内容
	 */
	public static void share(Context context, String content) {
		Intent intent = new Intent("android.intent.action.SEND");
		intent.setType("text/plain");
		// intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "分享到"));
	}

	/**
	 * 分享带图片的信息
	 * 
	 * @param content
	 *            分享内容
	 * @param imgPath
	 *            图片路径，如"file:////sdcard//8.png"，注意分享结束后该文件可能被删除，所以请使用临时文件，
	 *            并分享结束后判断并删除该临时文件
	 */
	public static void share(Context context, String content, String imgPath) {
		Intent intent = new Intent("android.intent.action.SEND");

		intent.setType("image/*");
		// intent.setType("image/png");
		// intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.putExtra("sms_body", content);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imgPath));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "分享到"));
	}
}
