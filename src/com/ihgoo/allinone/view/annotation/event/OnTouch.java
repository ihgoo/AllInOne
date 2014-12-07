package com.ihgoo.allinone.view.annotation.event;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = View.OnTouchListener.class,
        listenerSetter = "setOnTouchListener",
        methodName = "onTouch")
public @interface OnTouch {
    int[] value();

    int[] parentId() default 0;
}
