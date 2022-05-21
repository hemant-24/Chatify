package com.example.chatify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<user>
    private lateinit var adapter : userAdapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDBref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        mDBref = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        adapter = userAdapter(this, userList)
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter
        mDBref.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currUser = postSnapshot.getValue(user::class.java)
                    if(mAuth.currentUser?.uid != currUser?.uid){
                        userList.add(currUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LogIn::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}