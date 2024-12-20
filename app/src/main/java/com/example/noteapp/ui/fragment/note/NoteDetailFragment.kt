package com.example.noteapp.ui.fragment.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {
    private lateinit var binding: FragmentNoteDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBackListener()
        setUpListener()
        textWatcher()
        currentDateTime()
    }

    private fun setUpBackListener() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpListener() = with(binding) {
        txtReady.setOnClickListener {
            val etTitle = etTitle.text.toString()
            val etText = etText.text.toString()
            val currentDate = txtDate.text.toString()
            val currentTime = txtTime.text.toString()

            App.appDatabase?.noteDao()?.insertNote(NoteModel(etTitle, etText, currentDate, currentTime))
            findNavController().navigateUp()
        }
    }

    private fun currentDateTime() {
        val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
        binding.txtDate.text = currentDate

        val currentTime =SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.txtTime.text = currentTime
    }

    private fun textWatcher() = with(binding){
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = etTitle.text.isNotEmpty()
                val description = etText.text.isNotEmpty()
                txtReady.visibility=
                    if (title && description) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        etTitle.addTextChangedListener(textWatcher)
        etText.addTextChangedListener(textWatcher)
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }
}