package com.example.todoapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.utils.Constants

class AddTaskActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtAddText: EditText
    private lateinit var btnAdd: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        initControls()
    }

    private fun initControls() {
        edtAddText = findViewById(R.id.edt_addText)
        btnAdd = findViewById(R.id.button_add)

        btnAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_add -> {
                if (!edtAddText.text.toString().equals("")) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(Constants.TASKVALUE, edtAddText.text.toString().trim())
                    setResult(1, intent)
                    finish()
                } else {
                    edtAddText.setError(R.string.error_task_require.toString())
                    edtAddText.requestFocus()
                }
            }
        }
    }
}