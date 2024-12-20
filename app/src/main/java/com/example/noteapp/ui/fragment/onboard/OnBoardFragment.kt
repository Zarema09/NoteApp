package com.example.noteapp.ui.fragment.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.OnBoardViewpagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpListeners()
    }

    private fun setUpListeners() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.txtSkip.visibility = View.INVISIBLE
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.txtSkip.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                    binding.txtSkip.setOnClickListener {
                        setCurrentItem(currentItem + 2, true)
                    }
                }
            }
        })
        binding.txtSkip.setOnClickListener {
            if (currentItem < 3) {
                setCurrentItem(currentItem + 2, true)
            }
        }
        binding.btnStart.setOnClickListener {
            if (binding.viewpager2.currentItem == 2) {
                findNavController().navigate(R.id.noteFragment)
            }
        }
    }

    private fun initialize() {
        binding.viewpager2.adapter = OnBoardViewpagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = "   $position"
        }.attach()
    }
}