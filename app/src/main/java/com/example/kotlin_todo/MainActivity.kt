package com.example.kotlin_todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var model:TodoModel
    lateinit var dbHelper:DbHelper
    lateinit var modelList:ArrayList<TodoModel>
    lateinit var taskET: EditText
    lateinit var todoListView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskET=findViewById(R.id.tastEt)
        todoListView=findViewById(R.id.taskList)
        model= TodoModel()
        dbHelper= DbHelper(this)
        modelList= ArrayList<TodoModel>()
    }

    fun save(view: View){
        var task:String?=taskET.text.toString()
        model.task= task.toString()
        dbHelper.insertTask(model)

        modelList=dbHelper.getAllTask()
        val alist=ArrayList<String> ()
        for(mList:TodoModel in modelList){
            alist.add(mList.task.toString())
        }
        val  adapter= ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,alist)
        todoListView.adapter=adapter
}}
