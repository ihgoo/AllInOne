package com.ihgoo.allinone.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ihgoo.allinone.cache.ImageSDCacher;
import com.ihgoo.allinone.support.LogUtils;
import com.ihgoo.allinone.support.ScreenUtil;
import com.ihgoo.allinone.support.ScreenUtil.Screen;

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
	
	 /**
     * 递归的回收view下的所有bitmap 建议运行在 Activity.onDestory()
     *
     * @param view
     */
    public static void recyleBitmapRecursively(final View view) {
        if (view != null) {
            view.setBackgroundDrawable(null);
        }
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(null);
        } else if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                recyleBitmapRecursively(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    /**
     * 高清图与普通图相差大小
     *
     * @param path
     * @return 高清图/普通图
     */
    public static long getSaveSize(String path) {
        File file = new File(path);
        long size = file.length();
        if (size == 0) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                byte buffer[] = new byte[1024];
                size = 0;
                int num = 0;
                while ((num = in.read(buffer)) > 0) {
                    size += num;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        InputStream temp = null;
        try {
            temp = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        int with = options.outWidth;
        int heigth = options.outHeight;
        int max = with > heigth ? with : heigth;
        float savePercent = 0;
        if (max > 1080 && max < 1600) {
            float px_percent_1 = ((float) (max * 1.0)) / with;
            float px_percent_2 = ((float) (1024 * 1.0)) / with;
            savePercent = (float) (px_percent_1 * px_percent_1 * 0.8 * 0.8 - px_percent_2 * px_percent_2 * 0.4);
        } else if (max >= 1600) {
            float px_percent_1 = ((float) (1600 * 1.0)) / with;
            float px_percent_2 = ((float) (1024 * 1.0)) / with;
            savePercent = (float) (px_percent_1 * px_percent_1 * 0.8 * 0.8 - px_percent_2 * px_percent_2 * 0.4);
        } else {
            savePercent = (float) 0.4;
        }
        return (long) (size * savePercent);
    }

    /**
     * 指定大小的压缩
     *
     * @param path
     * @param limtMax
     * @return
     * @throws java.io.IOException
     */
    private static Bitmap revitionImageSize(String path, int limtMax) throws IOException {
        // 取得图片
        File file = new File(path);
        InputStream temp = new FileInputStream(file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        // 关闭流
        temp.close();
        // 生成压缩的图片
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // 这一步是根据要设置的大小，使宽和高都能满足
            if ((options.outWidth >> i <= limtMax) && (options.outHeight >> i <= limtMax)) {
                // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                temp = new FileInputStream(file);
                // 这个参数表示 新生成的图片为原始图片的几分之一。
                options.inSampleSize = (int) Math.pow(2.0D, i);
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;
                options.inTargetDensity = 240;
                bitmap = BitmapFactory.decodeStream(temp, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * @param path
     * @param limitMax 限制最大边
     * @param quality  质量
     * @return
     */
    public static byte[] getAddBitmapByte(String path, int limitMax, int quality) {
        // 用于存储bitmap的字节数组
        int LIMIT_MAX_MARGIN = limitMax;
        byte[] data = null;
        Bitmap localBitmap = null;
        try {
            localBitmap = revitionImageSize(path, 2 * LIMIT_MAX_MARGIN);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (localBitmap != null) {
            int width = localBitmap.getWidth();
            int height = localBitmap.getHeight();
            if (width > LIMIT_MAX_MARGIN || height > LIMIT_MAX_MARGIN) {
                // 创建操作图片用的matrix对象
                Matrix matrix = new Matrix();
                // 计算宽高缩放率.
                float scaleWidth = 0;
                float scaleHeight = 0;
                int newWidth = 0;
                int newHeight = 0;
                if (width > height) {
                    newWidth = LIMIT_MAX_MARGIN;
                    newHeight = (LIMIT_MAX_MARGIN * height) / width;
                    scaleWidth = ((float) newWidth) / width;
                    scaleHeight = ((float) newHeight) / height;
                } else {
                    newHeight = LIMIT_MAX_MARGIN;
                    newWidth = (LIMIT_MAX_MARGIN * width) / height;
                    scaleWidth = ((float) newWidth) / width;
                    scaleHeight = ((float) newHeight) / height;
                }
                // 缩放图片动作
                matrix.postScale(scaleWidth, scaleHeight);
                // 创建缩放后的图片
                try {
                    localBitmap = Bitmap.createBitmap(localBitmap, 0, 0, (int) width, (int) height, matrix, true);
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 压缩图片质量
            localBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            data = bos.toByteArray();
            try {
                localBitmap.recycle();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 获取图片的旋转角度
     *
     * @param path
     * @return int
     * @Title: readPictureDegree
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片的bitmap角度
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * @param bmp
     * @param radius
     * @return
     */
    public static Bitmap getCroppedBitmap(Bitmap bmp, float radius) {
        Bitmap sbmp;
        int width = (int) radius;
        if (bmp.getWidth() != width || bmp.getHeight() != width) {
            sbmp = Bitmap.createScaledBitmap(bmp, width, width, false);
        } else {
            sbmp = bmp;
        }
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return output;
    }


    /**
     * 获取路径图片，缩放大小，返回缩放后的新bitmap
     *
     * @param path 图片路径
     * @param size 目标宽高
     * @return
     */
    public static Bitmap resizeBitmap(String path, int size) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return resizeBitmap(bitmap, size, size);
    }

    /**
     * 对图片资源进行缩放之后返回新Bitmap
     *
     * @param imgId drawable id
     * @param size  目标宽高，px
     * @return 新bitmap
     */
    public static Bitmap resizeBitmap(Context context,int imgId, int size) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        return resizeBitmap(bitmap, size, size);
    }

    /**
     * 对bitmap进行缩放之后返回新Drawable
     *
     * @param bitmap 源bitmap
     * @param w      目标宽度
     * @param h      目标高度
     * @return 新bitmap
     */
    public static Drawable resizeDrawable(Bitmap bitmap, int w, int h) {
        return new BitmapDrawable(resizeBitmap(bitmap, w, h));
    }

    /**
     * 对bitmap进行缩放之后返回新bitmap
     *
     * @param srcBitmap 源bitmap
     * @param w         目标宽度, px
     * @param h         目标高度, px
     * @return 新bitmap
     */
    public static Bitmap resizeBitmap(Bitmap srcBitmap, int w, int h) {
        if (srcBitmap == null) {
            return null;
        }
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 回收view的bitmap
     *
     * @param view
     */
    public static void recycleViewBitmap(View view) {
        if (view != null) {
            Drawable drawable = view.getBackground();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                view.setBackgroundDrawable(null);
                drawable.setCallback(null);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null) {
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    bitmap = null;
                }
            }
        }

    }

}
