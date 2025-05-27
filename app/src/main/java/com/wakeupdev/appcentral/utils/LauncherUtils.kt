package com.wakeupdev.appcentral.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.util.Log

object LauncherUtils {
    private const val LAUNCHER_UTILS_TAG = "LAUNCHER_UTILS_DEBUG"

    fun isDefaultLauncher(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)

        val resolveInfo: ResolveInfo?
        try {
            Log.e(
                LAUNCHER_UTILS_TAG,
                "Using PackageManager.resolveActivity with MATCH_DEFAULT_ONLY"
            )

            resolveInfo =
                context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        } catch (e: Exception) {
            Log.e(LAUNCHER_UTILS_TAG, "Error resolving activity", e)
            return false
        }


        if (resolveInfo?.activityInfo == null) {
            Log.e(LAUNCHER_UTILS_TAG, "ResolveInfo or ActivityInfo is NULL.")
            return false
        }

        val currentLauncherPackageName = resolveInfo.activityInfo.packageName
        val myAppPackageName = context.packageName

        val isMyAppDefault = myAppPackageName == currentLauncherPackageName
        Log.e(
            LAUNCHER_UTILS_TAG,
            "Is My App Default: $isMyAppDefault, current app: $currentLauncherPackageName, my app $myAppPackageName"
        )

        return isMyAppDefault
    }
}