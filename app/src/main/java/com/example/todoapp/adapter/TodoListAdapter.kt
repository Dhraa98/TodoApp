package com.example.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.TodoEntity
import java.util.zip.Inflater

class TodoListAdapter(
    val context: Context,
    var dataList: List<TodoEntity>,
    private val callbackDelete: (position: Int) -> Unit,
    private val callbackUpdate: (position: Int) -> Unit
) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val textView: TextView
        val textDelete: ImageView
        val textUpdate: ImageView

        init {

            textView = itemview.findViewById(R.id.text_name)
            textDelete = itemview.findViewById(R.id.text_delete)
            textUpdate = itemview.findViewById(R.id.text_update)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_todo_list, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TodoListAdapter.ViewHolder, position: Int) {
        holder.textView.text = dataList.get(position).title
        holder.textDelete.setOnClickListener {

            Toast.makeText(context, R.string.msg_delete, Toast.LENGTH_SHORT).show()
            callbackDelete(position)
        }

        holder.textUpdate.setOnClickListener {
            callbackUpdate(position)
        }
    }
}