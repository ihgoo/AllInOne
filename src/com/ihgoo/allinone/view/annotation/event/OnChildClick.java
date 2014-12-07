package com.ihgoo.allinone.view.annotation.event;

import android.widget.ExpandableListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = ExpandableListView.OnChildClickListener.class,
        listenerSetter = "setOnChildClickListener",
        methodName = "onChildClick")
public @interface OnChildClick {
    int[] value();

    int[] parentId() default 0;
}
