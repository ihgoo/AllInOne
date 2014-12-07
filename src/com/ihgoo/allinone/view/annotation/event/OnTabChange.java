package com.ihgoo.allinone.view.annotation.event;

import android.widget.TabHost;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = TabHost.OnTabChangeListener.class,
        listenerSetter = "setOnTabChangeListener",
        methodName = "onTabChange")
public @interface OnTabChange {
    int[] value();

    int[] parentId() default 0;
}
