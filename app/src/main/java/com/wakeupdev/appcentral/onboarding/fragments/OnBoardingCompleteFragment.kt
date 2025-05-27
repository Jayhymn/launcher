package com.wakeupdev.appcentral.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.transition.MaterialFadeThrough
import com.wakeupdev.appcentral.MainActivity
import com.wakeupdev.appcentral.databinding.FragmentOnBoardingCompleteBinding
import com.wakeupdev.appcentral.utils.PreferenceHelper

class OnboardingCompleteFragment : Fragment() {

    private var _binding: FragmentOnBoardingCompleteBinding? = null
    private val binding get() = _binding!!
    private val TAG = OnboardingCompleteFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        returnTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFinish.setOnClickListener {
            PreferenceHelper.setOnboardingComplete(requireActivity().application)
            Log.d(TAG, "Onboarding marked as complete.")
            startActivity(
                Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}