package com.ihgoo.allinone.view.annotation.event;

import android.preference.Preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = Preference.OnPreferenceChangeListener.class,
        listenerSetter = "setOnPreferenceChangeListener",
        methodName = "onPreferenceChange")
public @interface OnPreferenceChange {
    String[] value();
}
