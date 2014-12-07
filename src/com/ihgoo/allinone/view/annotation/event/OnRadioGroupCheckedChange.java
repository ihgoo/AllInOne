package com.ihgoo.allinone.view.annotation.event;

import android.widget.RadioGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = RadioGroup.OnCheckedChangeListener.class,
        listenerSetter = "setOnCheckedChangeListener",
        methodName = "onCheckedChanged")
public @interface OnRadioGroupCheckedChange {
    int[] value();

    int[] parentId() default 0;
}
