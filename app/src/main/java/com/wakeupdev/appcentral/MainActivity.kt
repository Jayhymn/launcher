package com.wakeupdev.appcentral

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wakeupdev.appcentral.adapter.AppAdapter
import com.wakeupdev.appcentral.data.AppInfo
import com.wakeupdev.appcentral.onboarding.OnboardingActivity
import com.wakeupdev.appcentral.onboarding.OnboardingStep
import com.wakeupdev.appcentral.utils.PreferenceHelper

//import com.wakeupdev.appcentral.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.appGrid)
        recyclerView.layoutManager = GridLayoutManager(this, 4) // 4 columns

        val apps = loadInstalledApps()
        recyclerView.adapter = AppAdapter(apps) { appInfo ->
            startActivity(appInfo.launchIntent)
        }
    }


    private fun loadInstalledApps(): List<AppInfo> {
        val pm = packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfos = pm.queryIntentActivities(intent, 0)
        return resolveInfos.map {
            val label = it.loadLabel(pm).toString()
            val icon = it.loadIcon(pm)
            val launchIntent = pm.getLaunchIntentForPackage(it.activityInfo.packageName)
                ?: Intent()
            AppInfo(label, icon, launchIntent)
        }.sortedBy { it.label.lowercase() }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called.")
        // Fallback check, primarily onCreate should handle the redirect.
        if (PreferenceHelper.getCurrentStep(this.application) != OnboardingStep.COMPLETE) {
            Log.w(TAG, "onResume: Onboarding still not complete AND activity not finishing. Redirecting.")
            val intent = Intent(this, OnboardingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }
            startActivity(intent)
            finish()
        }
    }
}
