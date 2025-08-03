-dontobfuscate
-allowaccessmodification

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
}

-keep class me.adzuki.activitylogger.neoforge.ActivityLoggerNF {
    *;
}

-keep class me.adzuki.activitylogger.neoforge.EventHandler {
    public static void on*(...);
}

-keepclassmembers public class **$$serializer {
    private ** descriptor;
}
