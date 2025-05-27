package com.wakeupdev.appcentral.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.transition.MaterialFadeThrough
import com.wakeupdev.appcentral.databinding.FragmentSetDefaultLauncherBinding // Your binding
import com.wakeupdev.appcentral.onboarding.OnboardingActivity
import com.wakeupdev.appcentral.onboarding.OnboardingStep
import com.wakeupdev.appcentral.onboarding.OnboardingViewModel
import com.wakeupdev.appcentral.utils.LauncherUtils
import com.wakeupdev.appcentral.utils.PreferenceHelper // For checking if onboarding already complete

class SetDefaultLauncherFragment : Fragment() {

    private var _binding: FragmentSetDefaultLauncherBinding? = null
    private val binding get() = _binding!!
    private val TAG = SetDefaultLauncherFragment::class.java.simpleName

    private lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        returnTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetDefaultLauncherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onboardingActivity = activity as? OnboardingActivity

        viewModel = onboardingActivity?.getOnboardingViewModel()
            ?: throw IllegalStateException("OnboardingActivity or ViewModel missing")

        binding.btnSetDefault.setOnClickListener {
            Log.d(TAG, "Set Default button clicked.")
            viewModel.markChooserOpened()
            openDefaultLauncherChooser()
        }

        viewModel.hasOpenedChooser.observe(viewLifecycleOwner) { hasOpened ->
            Log.d(TAG, "hasOpenedChooser observed: $hasOpened")
            binding.btnSetDefault.isEnabled = true
            binding.btnSetDefault.text = if (hasOpened) {
                "Retry Setting Default Launcher"
            } else {
                "Set as Default Launcher"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!LauncherUtils.isDefaultLauncher(requireContext())) {
            Log.i(TAG, "App is NOT default launcher.")
            binding.btnSetDefault.isEnabled = true
            return
        }

        val currentStep = viewModel.currentStep.value
        if (currentStep == OnboardingStep.SET_LAUNCHER) {
            Log.i(TAG, "Current step is SET_LAUNCHER. Advancing to next step.")
            viewModel.nextStep()
        } else {
            Log.i(TAG, "Current step is $currentStep. No action.")
        }
    }

    private fun openDefaultLauncherChooser() {
        binding.btnSetDefault.isEnabled = false
        startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
