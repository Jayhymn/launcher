package com.wakeupdev.appcentral.onboarding

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.wakeupdev.appcentral.R
import com.wakeupdev.appcentral.onboarding.fragments.OnboardingCompleteFragment
import com.wakeupdev.appcentral.onboarding.fragments.SetDefaultLauncherFragment
import com.wakeupdev.appcentral.onboarding.fragments.WelcomeFragment

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewModel: OnboardingViewModel
    private val TAG = "OnboardingActivity"

    fun getOnboardingViewModel(): OnboardingViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_on_boarding)

        viewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this)
        )[OnboardingViewModel::class.java]


        viewModel.currentStep.observe(this) { step ->
            step?.let {
                Log.d(TAG, "Current step observed: $it")
                showFragment(it)
            }
        }
    }

    private fun showFragment(step: OnboardingStep) {
        Log.d(TAG, "showFragment for step: $step")
        val fragment: Fragment = when (step) {
            OnboardingStep.WELCOME -> WelcomeFragment()
            OnboardingStep.SET_LAUNCHER -> SetDefaultLauncherFragment()
            OnboardingStep.COMPLETE -> OnboardingCompleteFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        // As per requirements, user can only go forward.
        // super.onBackPressed() // Comment out to disable back press
        Log.i(TAG, "Back press disabled during onboarding.")
        // moveTaskToBack(true)
    }
}
