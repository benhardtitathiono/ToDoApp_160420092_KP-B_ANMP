package com.ubaya160420092.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya160420092.todoapp.R
import com.ubaya160420092.todoapp.databinding.TodoItemLayoutBinding
import com.ubaya160420092.todoapp.model.Todo
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class TodoListAdapter (val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    class TodoViewHolder(var view: TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TodoItemLayoutBinding>(inflater, R.layout.todo_item_layout, parent, false)

        //val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo=todoList[position]

        var checktask = holder.view.checkTask
        checktask.text = todoList[position].title

        var imgEdit = holder.view.imageEdit

        checktask.setOnCheckedChangeListener{ compoundButton, isChecked ->
            if (isChecked == true){
                adapterOnClick(todoList[position])
            }
        }

        imgEdit.setOnClickListener {
            val action = ToDoListFragmentDirections.actionEditToDo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}
