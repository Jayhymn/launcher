package com.wakeupdev.appcentral.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.wakeupdev.appcentral.onboarding.OnboardingStep

object PreferenceHelper {
    private const val PREFS_NAME = "your_app_prefs"
    private const val PREF_CURRENT_STEP = "pref_current_step"

    private fun getSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCurrentStep(app: Application): OnboardingStep {
        val prefs = getSharedPreferences(app)
        val ordinal = prefs.getInt(PREF_CURRENT_STEP, OnboardingStep.WELCOME.ordinal)
        return OnboardingStep.entries.getOrNull(ordinal) ?: OnboardingStep.WELCOME
    }

    fun setCurrentStep(app: Application, step: OnboardingStep) {
        val prefs = getSharedPreferences(app)
        prefs.edit().putInt(PREF_CURRENT_STEP, step.ordinal).apply()
    }

    fun setOnboardingComplete(app: Application) {
        val prefs = getSharedPreferences(app)
        return prefs.edit().putInt("onboarding_complete", OnboardingStep.WELCOME.ordinal).apply()
    }
}

