package com.ihgoo.allinone.view.annotation.event;

import android.preference.Preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = Preference.OnPreferenceClickListener.class,
        listenerSetter = "setOnPreferenceClickListener",
        methodName = "onPreferenceClick")
public @interface OnPreferenceClick {
    String[] value();
}
