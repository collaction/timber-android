# RetroFit2
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

# Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# CarouselView
-keep class com.synnapps.carouselview.** { *; }