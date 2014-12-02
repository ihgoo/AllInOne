package com.ihgoo.allinone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ihgoo.allinone.cache.ImageSDCacher;
import com.ihgoo.allinone.util.ScreenUtil.Screen;

public class BitmapTool {

	private final static String TAG = BitmapTool.class.getSimpleName();

	/**
	 * 根据图像URL创建Bitmap
	 * 
	 * @param url
	 *            URL地址
	 * @return bitmap
	 */
	public Bitmap CreateImage(String url) {
		// Logger.d("ImageDownloader",
		// "开始调用CreateImage():" + System.currentTimeMillis());
		Bitmap bitmap = null;
		if (url == null || url.equals("")) {
			return null;
		}
		try {
			// Logger.d(
			// "ImageDownloader",
			// "C Before SDCard decodeStream==>" + "Heap:"
			// + (Debug.getNativeHeapSize() / 1024) + "KB "
			// + "FreeHeap:"
			// + (Debug.getNativeHeapFreeSize() / 1024) + "KB "
			// + "AllocatedHeap:"
			// + (Debug.getNativeHeapAllocatedSize() / 1024)
			// + "KB" + " url:" + url);

			FileInputStream fis = new FileInputStream(url);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
			opts.inTempStorage = new byte[100 * 1024];
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			bitmap = BitmapFactory.decodeStream(fis, null, opts);

			// Logger.d(
			// "ImageDownloader",
			// "C After SDCard decodeStream==>" + "Heap:"
			// + (Debug.getNativeHeapSize() / 1024) + "KB "
			// + "FreeHeap:"
			// + (Debug.getNativeHeapFreeSize() / 1024) + "KB "
			// + "AllocatedHeap:"
			// + (Debug.getNativeHeapAllocatedSize() / 1024)
			// + "KB" + " url:" + url);
		} catch (OutOfMemoryError e) {
			LogUtils.e("OutOfMemoryError", e);
			System.gc();
		} catch (FileNotFoundException e) {
			LogUtils.e("FileNotFoundException", e);
		}
		// Logger.d("ImageDownloader",
		// "结束调用CreateImage():" + System.currentTimeMillis());
		return bitmap;
	}

	/**
	 * 图片缩放处理,并保存到SDCard
	 * 
	 * @param byteArrayOutputStream
	 *            图片字节流
	 * @param screen
	 *            屏幕宽高
	 * @param url
	 *            图片网络路径
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是Jpg
	 * @return 缩放后的图片bitmap
	 */
	public static Bitmap saveZoomBitmapToSDCard(
			ByteArrayOutputStream byteArrayOutputStream, Screen screen,
			String url, String cachePath, boolean isJpg) {

		Bitmap bitmap = null;
		try {

			byte[] byteArray = byteArrayOutputStream.toByteArray();

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inTempStorage = new byte[16 * 1024];

			// 只加载图片的边界
			options.inJustDecodeBounds = true;

			// 获取Bitmap信息
			BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length,
					options);

			// 获取屏幕的宽和高
			int screenWidth = screen.widthPixels;
			int screenHeight = screen.heightPixels;

			// 屏幕最大像素个数
			int maxNumOfPixels = screenWidth * screenHeight;

			// 计算采样率
			int sampleSize = computeSampleSize(options, -1, maxNumOfPixels);

			options.inSampleSize = sampleSize;

			options.inJustDecodeBounds = false;

			// 重新读入图片,此时为缩放后的图片
			bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length, options);

			// 压缩比例
			int quality = 100;

			// 判断是否是Jpg,png是无损压缩,所以不用进行质量压缩
			if (bitmap != null && isJpg) {

				ByteArrayOutputStream saveBaos = new ByteArrayOutputStream();

				bitmap.compress(Bitmap.CompressFormat.JPEG, quality, saveBaos);

				// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				while (saveBaos.toByteArray().length / 1024 > 100) {

					// 重置saveBaos即清空saveBaos
					saveBaos.reset();

					// 每次都减少10
					quality -= 10;

					// 这里压缩optionsNum%，把压缩后的数据存放到saveBaos中
					bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
							saveBaos);

				}
				// 把压缩后的数据ByteArrayOutputStream存放到ByteArrayInputStream中
				ByteArrayInputStream saveBais = new ByteArrayInputStream(
						saveBaos.toByteArray());

				bitmap = BitmapFactory.decodeStream(saveBais, null, null);

			}

			// 保存到SDCard
			ImageSDCacher.getImageSDCacher().saveBitmapToSDCard(bitmap, url,
					cachePath, isJpg, quality);

		} catch (Exception e) {
			Log.e("saveZoomBitmapToSDCard", "" + e);
		}

		return bitmap;
	}

	/**
	 * 图片缩放处理,并保存到SDCard
	 * 
	 * @param screen
	 *            屏幕宽高
	 * @param bitmap
	 *            图片bitmap
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是Jpg
	 * @return 缩放后的图片bitmap
	 */
	public static Bitmap saveZoomBitmapToSDCard(Bitmap bitmap, Screen screen,
			String url, String cachePath, boolean isJpg) {
		Bitmap tempBitmap = null;
		byte[] byteArray = bitmap2Bytes(bitmap);
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();

			// 获取屏幕的宽和高
			int screenWidth = screen.widthPixels;
			int screenHeight = screen.heightPixels;

			// 屏幕最大像素个数
			int maxNumOfPixels = screenWidth * screenHeight;

			// 计算采样率
			int sampleSize = computeSampleSize(options, -1, maxNumOfPixels);

			options.inSampleSize = sampleSize;

			options.inJustDecodeBounds = false;

			// 重新读入图片,此时为缩放后的图片
			tempBitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length, options);

			// 压缩比例
			int quality = 100;

			// 判断是否是Jpg,png是无损压缩,所以不用进行质量压缩
			if (bitmap != null && isJpg) {

				ByteArrayOutputStream saveBaos = new ByteArrayOutputStream();

				tempBitmap.compress(Bitmap.CompressFormat.JPEG, quality,
						saveBaos);

				// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				while (saveBaos.toByteArray().length / 1024 > 100) {

					// 重置saveBaos即清空saveBaos
					saveBaos.reset();

					// 每次都减少10
					quality -= 10;

					// 这里压缩optionsNum%，把压缩后的数据存放到saveBaos中
					tempBitmap.compress(Bitmap.CompressFormat.JPEG, quality,
							saveBaos);

				}
				// 把压缩后的数据ByteArrayOutputStream存放到ByteArrayInputStream中
				ByteArrayInputStream saveBais = new ByteArrayInputStream(
						saveBaos.toByteArray());

				tempBitmap = BitmapFactory.decodeStream(saveBais, null, null);

			}

			// 保存到SDCard
			ImageSDCacher.getImageSDCacher().saveBitmapToSDCard(tempBitmap,
					url, cachePath, isJpg, quality);

		} catch (Exception e) {
			Log.e("", e.getMessage());
		}

		return tempBitmap;
	}

	public static byte[] bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// Recycle the resource of the Image
	public void recycleImage(Bitmap bitmap) {
		try {
			if (bitmap != null && !bitmap.isMutable() && !bitmap.isRecycled()) {
				bitmap.recycle();
				System.gc();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("bitmap recycle excpetion", e);
		}
	}

	/**
	 * 替换特殊字符
	 * 
	 * @param fileName
	 *            图片的处理前的名字
	 * @return 图片处理后的名字
	 */
	public static String renameUploadFile(String fileName) {

		String result = "yepcolor";

		if (fileName != null && !fileName.equals("")) {

			result = fileName.hashCode() + "";// 获得文件名称的hashcode值

		}
		return result;
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(fileName);
		// result = m.replaceAll("").trim();

	}

	/**
	 * 计算采样率
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {

		int initialSize = computeInitialSampleSize(options, minSideLength,

		maxNumOfPixels);

		int roundedSize;

		if (initialSize <= 8) {

			roundedSize = 1;

			while (roundedSize < initialSize) {

				roundedSize <<= 1;

			}

		} else {

			roundedSize = (initialSize + 7) / 8 * 8;

		}

		return roundedSize;

	}

	/**
	 * 计算初始采样率
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {

		double w = options.outWidth;

		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 :

		(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 :

		(int) Math.min(Math.floor(w / minSideLength),

		Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {

			// return the larger one when there is no overlapping zone.

			return lowerBound;

		}

		if ((maxNumOfPixels == -1) &&

		(minSideLength == -1)) {

			return 1;

		} else if (minSideLength == -1) {

			return lowerBound;

		} else {

			return upperBound;

		}

	}
	/**
	 * 根据图片的路径获取图片的大小
	 * 
	 * @param item
	 */
	// public static void getBitmapSize(Items item) {
	// URL url;
	// try {
	// url = new URL(item.getPicUrl());
	// URLConnection conn = url.openConnection();
	// conn.connect();
	// InputStream is = conn.getInputStream();
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// BitmapFactory.decodeStream(is, null, options);
	// options.inJustDecodeBounds = true;
	// int height = options.outHeight;
	// int width = options.outWidth;
	// item.setImageWidth(width);
	// item.setImageHeight(height);
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

}
