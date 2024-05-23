package com.oscargil80.recyviewandroidsoluction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oscargil80.recyviewandroidsoluction.model.UserData
import com.oscargil80.recyviewandroidsoluction.view.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()

        addsBtn = findViewById(R.id.addingBtn)
        recycler = findViewById(R.id.mRecycler)

        userAdapter = UserAdapter(this, userList)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = userAdapter

        addsBtn.setOnClickListener { addInfo() }


    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item, null)

        val userName = v.findViewById<EditText>(R.id.userName)
        val userNo = v.findViewById<EditText>(R.id.userNo)


        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            val names = userName.text.toString()
            val number = userNo.text.toString()
            userList.add(UserData("Name: $names", "Mobile: $number"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Adding Number Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()


        }
        addDialog.create()
        addDialog.show()

    }


}