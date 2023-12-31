package com.example.todoappkpc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoappkpc.R
import com.example.todoappkpc.viewmodel.DetailTodoViewModel

class EditFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        val txtJudulTodo = view.findViewById<TextView>(R.id.txtJudulTodo)
        txtJudulTodo.text = "Edit Todo"

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.text = "Save"

        val uuid = EditFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        btnAdd.setOnClickListener {
            val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtNotes)
            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(), radioButton.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }

        observeViewModel()
    }

    fun observeViewModel (){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            val txtTitle = view?.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view?.findViewById<EditText>(R.id.txtNotes)
            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)

            val high = view?.findViewById<RadioButton>(R.id.radioHigh)
            val med = view?.findViewById<RadioButton>(R.id.radioMedium)
            val low = view?.findViewById<RadioButton>(R.id.radioLow)

            when(it.priority){
                1 -> low?.isChecked = true
                2 -> med?.isChecked = true
                3 -> high?.isChecked = true
            }
        })
    }

}