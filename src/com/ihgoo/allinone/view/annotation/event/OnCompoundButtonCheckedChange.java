package com.ihgoo.allinone.view.annotation.event;

import android.widget.CompoundButton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = CompoundButton.OnCheckedChangeListener.class,
        listenerSetter = "setOnCheckedChangeListener",
        methodName = "onCheckedChanged")
public @interface OnCompoundButtonCheckedChange {
    int[] value();

    int[] parentId() default 0;
}
