package com.ubaya160420092.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ubaya160420092.todoapp.R
import com.ubaya160420092.todoapp.viewmodel.ListTodoViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ToDoListFragment : Fragment() {

    private lateinit var viewModel: ListTodoViewModel
    private val todoListAdapter  = TodoListAdapter(arrayListOf(), { item -> viewModel.clearTask(item)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        val recViewTodo = view.findViewById<RecyclerView>(R.id.recViewToDo)
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = todoListAdapter

        val fabAddTodo=view.findViewById<FloatingActionButton>(R.id.fabAddToDo)

        fabAddTodo.setOnClickListener {
            val action = ToDoListFragmentDirections.actionCreateToDo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            var txtEmpty = view?.findViewById<TextView>(R.id.textEmpty)
            if(it.isEmpty()) {
                txtEmpty?.visibility = View.VISIBLE
            } else {
                txtEmpty?.visibility = View.GONE
            }
        })
    }
}