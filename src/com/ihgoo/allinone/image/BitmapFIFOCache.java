package com.ihgoo.allinone.image;

import java.util.HashMap;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

/**
 * 先进先出bitmap缓存
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class BitmapFIFOCache {

	private Queue<String> mCacheQueue;
	private HashMap<String, Ref> mCache;

	/**总共缓存最大空间，单位kb**/
	private int mCacheMaxSize;
	
	/**现在缓存中占用的大小,单位kb**/
	private int mCurrtSize = 0;
	
	
	public BitmapFIFOCache(int cacheMaxSize) {
		if (0 <= cacheMaxSize) {
			throw new IllegalArgumentException(
					"CacheMaxSize must be greater than 0");
		}
		
		mCacheMaxSize = cacheMaxSize;
	}

	public void putBitmap(String key, Bitmap bitmap) {
		putIntoCache(key, bitmap);
	}

	private void putIntoCache(String key, Bitmap bitmap) {
		if (mCache.containsKey(key)) {
			return;
		}

		if (mCacheQueue.contains(key)) {
			mCacheQueue.remove(key);
			mCache.remove(key);
			mCacheQueue.offer(key);
			mCache.put(key, new Ref(bitmap, this.sizeOf(bitmap)));
		} else {
			putIntoQueue(key, bitmap);
		}
	}
	
	private void putIntoQueue(String key,Bitmap bitmap){
		if (this.hasFree(bitmap)) {	// 有空闲空间
			mCurrtSize = mCurrtSize + this.sizeOf(bitmap); // 统计缓存大小
			mCacheQueue.offer(key);	// 从头插入
			mCache.put(key, new Ref(bitmap, this.sizeOf(bitmap)));
		}else {
			String pollKey = mCacheQueue.poll(); // poll队列最后一个值
			mCurrtSize = mCurrtSize - mCache.get(pollKey).mSize; // 计算目前缓存大小
			mCache.remove(pollKey);	//移除掉最后一个
			this.putIntoCache(key, bitmap); // 递归插入
		}
	}

	private boolean hasFree(Bitmap bitmap){
		int bitmapSize = this.sizeOf(bitmap);
		if (mCacheMaxSize <= bitmapSize + mCurrtSize) {
			return false;
		}
		return true;
	}
	
	/**
	 * Cal the bitmap size;
	 * 
	 * @param bitmap
	 * @return size of kb.
	 */
	private int sizeOf(Bitmap bitmap) {
		Config config = bitmap.getConfig();
		int size = 0;
		if (config == Bitmap.Config.ALPHA_8) {
			size = 1;
		} else if (config == Bitmap.Config.ARGB_4444) {
			size = 2;
		} else if (config == Bitmap.Config.ARGB_8888) {
			size = 4;
		} else if (config == Bitmap.Config.RGB_565) {
			size = 2;
		}
		return bitmap.getWidth() * bitmap.getHeight() * size / 1024;
	}

	public Bitmap getBitmap(String key) {
		
		if (mCache.get(key) == null) {
			return null;
		}else {
			return mCache.get(key).mBitmap;
		}
		
	}

	class Ref {
		private Bitmap mBitmap;
		private int mSize;

		public Ref(Bitmap bitmap, int size) {
			mBitmap = bitmap;
			mSize = size;
		}
	}
	

}
