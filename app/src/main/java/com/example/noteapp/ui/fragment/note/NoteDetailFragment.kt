package com.example.noteapp.ui.fragment.note

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    private var noteId: Int = -1
    private lateinit var noteContainerRelative: RelativeLayout
    private lateinit var noteContainerLinear: LinearLayout// Контейнер для заметки, фон которого будем менять
    private lateinit var threeDotsButton: ImageView  // Кнопка с тремя точками

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Привязываем элементы
       noteContainerLinear = view.findViewById(R.id.note_container_linear)
        noteContainerRelative = view.findViewById(R.id.note_container_relative)
        threeDotsButton = view.findViewById(R.id.img_setColor)

        updateNote()
        setUpBackListener()
        setUpListener()
        textWatcher()
        currentDateTime()
        // Обработчик нажатия на кнопку с тремя точками
        threeDotsButton.setOnClickListener {
            showColorPicker(it)
        }
    }


    private fun updateNote() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val id = App.appDatabase?.noteDao()?.getById(noteId)
            id?.let { model ->
                binding.etTitle.setText(model.title)
                binding.etText.setText(model.description)
            }
        }
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
            if (noteId != -1) {
                val updateNote = NoteModel(etTitle, etText , currentDate,currentTime)
                updateNote.id = noteId
                App.appDatabase?.noteDao()?.updateNote(updateNote)
            } else {
                App.appDatabase?.noteDao()?.insertNote(NoteModel(etTitle, etText,currentDate,currentTime))
            }
            App.appDatabase?.noteDao()
                ?.insertNote(NoteModel(etTitle, etText, currentDate, currentTime))
            findNavController().navigateUp()
        }
    }

    private fun currentDateTime() {
        val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
        binding.txtDate.text = currentDate

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.txtTime.text = currentTime
    }

    private fun textWatcher() = with(binding) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = etTitle.text.isNotEmpty()
                val description = etText.text.isNotEmpty()
                txtReady.visibility =
                    if (title && description) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        etTitle.addTextChangedListener(textWatcher)
        etText.addTextChangedListener(textWatcher)
    }

    private fun showColorPicker(anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.color_picker_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.color_red -> {
                    changeNoteBackgroundColor(Color.RED)
                    true
                }
                R.id.color_blue -> {
                    changeNoteBackgroundColor(Color.BLUE)
                    true
                }
                R.id.color_green -> {
                    changeNoteBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                    true
                }
                R.id.color_yellow -> {
                    changeNoteBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                    true
                }
                R.id.color_purple -> {
                    changeNoteBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple))
                    true
                }
                R.id.color_orange -> {
                    changeNoteBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun changeNoteBackgroundColor(color: Int) {
        // Меняем цвет фона контейнера
        noteContainerRelative.setBackgroundColor(color)
        noteContainerLinear.setBackgroundColor(color)

        // Сохраняем выбранный цвет фона в SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("NoteApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("note_background_color", color)  // Сохраняем выбранный цвет
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("NoteApp", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("note_background_color", Color.WHITE) // Цвет по умолчанию
        noteContainerLinear.setBackgroundColor(savedColor)
    noteContainerRelative.setBackgroundColor(savedColor)// Применяем сохраненный цвет фона
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}