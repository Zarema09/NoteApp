package com.example.noteapp.ui.fragment.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoardPosition"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize(): Unit = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                animationView.setAnimation(R.raw.lottie1)
                txtTitle.text = "Удобство"
                txtBody.text = "Cоздавайте заметки в два клика!Записывайте мысли идеи и важные задачи."
            }

            1 -> {
                animationView.setAnimation(R.raw.lottie2)
                txtTitle.text = "Организация"
                txtBody.text = "Организуйте заметки по папкам и тегам.Легко находите нужную информация в любое время."
            }

            2 -> {
                animationView.setAnimation(R.raw.lottie3)
                txtTitle.text = "Синхронизация"
                txtBody.text = "Синхронизация на всех устройствах.Доступ к записям в любое время и в любом месте."
            }
        }
    }
}