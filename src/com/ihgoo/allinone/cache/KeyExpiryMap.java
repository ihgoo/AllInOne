package com.ihgoo.allinone.cache;

import java.util.concurrent.ConcurrentHashMap;

public class KeyExpiryMap<K, V> extends ConcurrentHashMap<K, Long> {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

	public KeyExpiryMap(int initialCapacity, float loadFactor,
			int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}

	public KeyExpiryMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
	}

	public KeyExpiryMap(int initialCapacity) {
		super(initialCapacity);
	}

	public KeyExpiryMap() {
		super();
	}

	@Override
	public synchronized Long get(Object key) {
		if (this.containsKey(key)) {
			return super.get(key);
		} else {
			return null;
		}
	}

	@Override
	public synchronized Long put(K key, Long expiryTimestamp) {
		if (this.containsKey(key)) {
			this.remove(key);
		}
		return super.put(key, expiryTimestamp);
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		boolean result = false;
		Long expiryTimestamp = super.get(key);
		if (expiryTimestamp != null
				&& System.currentTimeMillis() < expiryTimestamp) {
			result = true;
		} else {
			this.remove(key);
		}
		return result;
	}

	@Override
	public synchronized Long remove(Object key) {
		return super.remove(key);
	}

	@Override
	public synchronized void clear() {
		super.clear();
	}

}
