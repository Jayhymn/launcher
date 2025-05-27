package com.wakeupdev.appcentral.onboarding

enum class OnboardingStep {
    WELCOME,
    SET_LAUNCHER,
    COMPLETE;

    fun next(): OnboardingStep? = when (this) {
        WELCOME -> SET_LAUNCHER
        SET_LAUNCHER -> COMPLETE
        COMPLETE -> null // End of onboarding
    }
}