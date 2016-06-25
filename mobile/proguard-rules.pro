-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontnote
-dontwarn
-dontshrink
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-libraryjars /Users/remychantenay/Documents/workspace/Firebucket/mobile/libs
-keepdirectories /Users/remychantenay/Documents/workspace/Firebucket/mobile/libs
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment

# Lib exceptions
-keep class org.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v13.app.** { *; }
-keep interface android.support.v13.app.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep class !android.support.v7.internal.view.menu.**,** {*;}
-keep class de.neofonie.** { *; }
-keep interface de.neofonie.** { *; }
-keep class com.google.android.** { *; }
-keep interface com.google.android.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.** { *; }
-keep interface com.google.gson.** { *; }
-keep class com.nineoldandroids.** { *; }
-keep interface com.nineoldandroids.** { *; }


# Gson specific
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }

-keep class java.util.** { *; }
-keep class * implements java.io.Serializable { *; }
-keepattributes *Annotation*
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable { *; }

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


# --- Allows to debug easily
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# --- Analytics, YouTube library
-keep class com.google.** { *; }
-keep interface com.google.** { *; }

# --- ComScore
-keep class com.comscore.** { *; }
-keep interface com.comscore.** { *; }
-dontwarn com.comscore.**

# --- ListView animations library
-keep class com.nhaarman.** { *; }
-keep interface com.nhaarman.** { *; }


# --- Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class com.android.gms.** { *; }
-keep interface com.android.gms.** { *; }
-keep class com.google.android.gms.** { *; }
-keep interface com.google.android.gms.** { *; }



# Keep native methods
-keepclassmembers class * {
    native <methods>;
}


# --- Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


# Android Binding
-keep class com.android.binding.** { *; }
-keep interface com.android.binding.** { *; }


# Support v7 preferences
-keep public class android.support.v7.preference.Preference { *; }
-keep public class * extends android.support.v7.preference.Preference { *; }

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn rx.**
-dontwarn retrofit.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}


# Glide image library
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Retrofit 2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Solution for NoClassDefFoundError (@ActionMenuView:getMenu:642) on SOME devices
# https://github.com/google/iosched/issues/79
# http://stackoverflow.com/questions/24809580/noclassdeffounderror-android-support-v7-internal-view-menu-menubuilder/27254191#27254191
-keep class !android.support.v7.internal.view.menu.* implements android.support.v4.internal.view.SupportMenu, android.support.v7.** {*;}

# Solution for InflateException (with NavigationView) on SOME devices
# Samsung Android 4.2 but MOSTLY Wiko 4.2 devices
# https://code.google.com/p/android/issues/detail?id=78377 (see #308)
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
