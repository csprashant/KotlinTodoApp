package com.example.kotlin_todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class DbHelper( val context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    lateinit var db:SQLiteDatabase
    companion object{
        val DATABASE_NAME="TODO_DATABASE"
        val TABLE_NAME="TODO_TABLE"
        val DATABASE_VERSION=1
        val COL_1="ID"
        val COL_2="TASK"
        val TAG="DbHelper"
    }
    override fun onCreate(db: SQLiteDatabase?) {
    try{
        val QRY:String="CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COL_1 INTEGER PRIMARY KEY AUTOINCREMENT , $COL_2 TEXT)"
        db?.execSQL(QRY)


    }catch (e:Exception){
        Log.e(TAG,e.message.toString())
        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
    }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try{
            val QRY="DROP TABLE IF EXISTS $TABLE_NAME"
            db?.execSQL(QRY)
            onCreate(db)

        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
        }
    }
    fun insertTask(model:TodoModel){
        try{
            db=this.writableDatabase
            val values:ContentValues=ContentValues()
            values.put(COL_2,model.task)
            db.insert(TABLE_NAME,null,values)
            Toast.makeText(context,"Record inserted",Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception){
            Log.e(TAG,e.message.toString())
            Toast.makeText(context,"Error in Record insertion",Toast.LENGTH_SHORT).show()
        }
    }
    fun updateTask(id:Integer,task:String){
        try{
            db=this.writableDatabase
            val values:ContentValues=ContentValues()
            values.put(COL_2,task)
            db.update(TABLE_NAME,values,"id?",arrayOf("$id"))
            Toast.makeText(context,"Task updated ",Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception){
            Log.e(TAG,e.message.toString())
            Toast.makeText(context,"Error in Record insertion",Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteTask(id:Integer){
        try{
            db=this.writableDatabase
            db.delete(TABLE_NAME,"id?",arrayOf("$id"))
            Toast.makeText(context,"Task deleted",Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception){
            Log.e(TAG,e.message.toString())
            Toast.makeText(context,"Error in Record insertion",Toast.LENGTH_SHORT).show()
        }
    }
    fun getAllTask():ArrayList<TodoModel>{
        db=this.readableDatabase
        var cur: Cursor?=null
        var list=ArrayList<TodoModel>()
        db.beginTransaction()
        try{
            cur=db.query(TABLE_NAME,null,null,null,null,null,null)
            if(cur!=null){
                if(cur.moveToFirst()){
                    do{
                        var model=TodoModel()
                        model.id=cur.getInt(0) as Integer
                        model.task=cur.getString(1)
                        list.add(model)
                    }while(cur.moveToNext())
                }
            }
        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
            Toast.makeText(context,"Error in Record insertion",Toast.LENGTH_SHORT).show()
        }finally {
                db.endTransaction()
            if (cur != null) {
                cur.close()
            }
        }

        return list
    }
}