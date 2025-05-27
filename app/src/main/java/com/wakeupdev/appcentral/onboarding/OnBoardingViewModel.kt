package com.wakeupdev.appcentral.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.wakeupdev.appcentral.utils.PreferenceHelper

//import com.wakeupdev.appcentral.utils.PreferenceHelper

class OnboardingViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    companion object {
        private const val KEY_HAS_OPENED_CHOOSER = "hasOpenedChooser_sdf"
    }

    private val _currentStep = MutableLiveData(OnboardingStep.WELCOME)
    val currentStep: LiveData<OnboardingStep> = _currentStep

    val hasOpenedChooser: LiveData<Boolean> =
        savedStateHandle.getLiveData(KEY_HAS_OPENED_CHOOSER, false)

    init {
        val savedStep = PreferenceHelper.getCurrentStep(application)
        _currentStep.value = savedStep
    }

    fun nextStep() {
        val current = _currentStep.value ?: OnboardingStep.WELCOME
        if (current == OnboardingStep.COMPLETE) {
            return
        }
        current.next()?.let {
            _currentStep.value = it
            PreferenceHelper.setCurrentStep(getApplication(), it)
        }
    }

    fun reset() {
        _currentStep.value = OnboardingStep.WELCOME
    }

    fun markChooserOpened() {
        savedStateHandle[KEY_HAS_OPENED_CHOOSER] = true
    }
}

