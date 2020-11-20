package com.example.todoapp.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoListAdapter
import com.example.todoapp.database.TodoEntity
import com.example.todoapp.database.TodoRoomDatabase
import com.example.todoapp.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"
    private var mUpdatedText: String = ""
    private var flag: Int = 0
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var rvList: RecyclerView
    private lateinit var adapter: TodoListAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var langDialogView: AlertDialog

    private val dataList: MutableList<TodoEntity> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initControls()
    }

    private fun initControls() {
        btnAdd = findViewById(R.id.button_add)
        rvList = findViewById(R.id.rv_List)
        btnAdd.setOnClickListener(this)



        if (TodoRoomDatabase.getDatabase(this).todoDao().getAll().size > 0) {
            Log.e(
                TAG,
                "initialiseAdapter: " + TodoRoomDatabase.getDatabase(this).todoDao().getAll()
            )
            initialiseAdapter()
        }


    }

    private fun initialiseAdapter() {

        if (flag == 0) {

        } else {
            TodoRoomDatabase.getDatabase(this).todoDao().getAll().forEach()
            {
                dataList.addAll(listOf(it))
                Log.i("Fetch Records", "Id:  : ${it.Id}")
                Log.i("Fetch Records", "Name:  : ${it.title}")
            }
        }

        adapter = TodoListAdapter(this, dataList, { position ->

            Log.e(TAG, "initialiseAdapter: " + dataList[position].title)

            TodoRoomDatabase.getDatabase(this).todoDao().delete(dataList[position])

            Log.e(
                TAG,
                "initialiseAdapter: " + TodoRoomDatabase.getDatabase(this).todoDao().getAll().size
            )



            adapter.notifyItemRemoved(position)
            adapter.notifyDataSetChanged()
            dataList.removeAt(position)

        }, { position ->
            showDialog(dataList[position].title, position)

        })
        manager = LinearLayoutManager(this)
        rvList.adapter = adapter
        rvList.layoutManager = manager
        adapter.notifyDataSetChanged()


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_add -> {
                val intent = Intent(this, AddTaskActivity::class.java)
                startActivityForResult(intent, 1)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (data != null) {


                var mText = data!!.getStringExtra(Constants.TASKVALUE)
                isertAll(mText!!)
            }
        }

    }

    private fun isertAll(sText: String) {


        //Insert Case
        var todoEntity = TodoEntity()
        /* val thread = Thread {*/


        todoEntity.title = sText

        dataList.clear()
        TodoRoomDatabase.getDatabase(this).todoDao().insertAll(todoEntity)
        TodoRoomDatabase.getDatabase(this).todoDao().getAll().forEach()
        {
            dataList.addAll(listOf(it))
            Log.i("Fetch Records", "Id:  : ${it.Id}")
            Log.i("Fetch Records", "Name:  : ${it.title}")
        }


        /*  }
          thread.start()
  */

        if (this::adapter.isInitialized) {

            adapter.notifyDataSetChanged()
        } else {
            flag == 1
            initialiseAdapter()
        }


    }

    private fun showDialog(title: String, position: Int) {
        val factory = LayoutInflater.from(this)
        val deleteDialogView: View =
            factory.inflate(R.layout.dialog_update, null)

        langDialogView = AlertDialog.Builder(this).create()
        langDialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        langDialogView.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        langDialogView.setView(deleteDialogView)
        val textUpdated = deleteDialogView.findViewById(R.id.text_updated) as TextView
        textUpdated.text = title
        val buttonDone = deleteDialogView.findViewById(R.id.button_done) as Button

        buttonDone.setOnClickListener {
            if (!textUpdated.text.toString().equals("")) {
                langDialogView.dismiss()
                mUpdatedText = textUpdated.text.toString()
                var todoEntity = TodoEntity()
                todoEntity.Id = dataList[position].Id
                todoEntity.title = mUpdatedText

                val thread = Thread {
                    TodoRoomDatabase.getDatabase(this).todoDao().updateTodo(todoEntity)
                }
                thread.start()

                dataList.set(position, todoEntity);
                adapter.notifyItemChanged(position)
            }

        }

        langDialogView.show()
    }


}