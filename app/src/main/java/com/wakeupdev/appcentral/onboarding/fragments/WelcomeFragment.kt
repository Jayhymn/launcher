package com.wakeupdev.appcentral.onboarding.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialFadeThrough
import com.wakeupdev.appcentral.R
import com.wakeupdev.appcentral.databinding.FragmentWelcomeBinding
import com.wakeupdev.appcentral.onboarding.OnboardingActivity
import com.wakeupdev.appcentral.onboarding.OnboardingViewModel

class WelcomeFragment : Fragment() {

    private val TAG = WelcomeFragment::class.java.simpleName
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onboardingActivity = activity as? OnboardingActivity

        viewModel = onboardingActivity?.getOnboardingViewModel()
            ?: throw IllegalStateException("OnboardingActivity or ViewModel missing")

        binding.btnContinue.setOnClickListener {
            Log.d(TAG, "Continue clicked, calling viewModel.nextStep()")
            viewModel.nextStep()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
