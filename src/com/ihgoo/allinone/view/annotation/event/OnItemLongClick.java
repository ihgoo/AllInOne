package com.ihgoo.allinone.view.annotation.event;

import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = AdapterView.OnItemLongClickListener.class,
        listenerSetter = "setOnItemLongClickListener",
        methodName = "onItemLongClick")
public @interface OnItemLongClick {
    int[] value();

    int[] parentId() default 0;
}
