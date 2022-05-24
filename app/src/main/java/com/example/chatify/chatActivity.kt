package com.example.chatify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class chatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendBtn : ImageView
    private lateinit var messageadapter : messageAdapter
    private lateinit var msgList : ArrayList<message>
    private lateinit var mDBref : DatabaseReference
    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDBref = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        supportActionBar?.title = name
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.sendBtn)
        msgList  = ArrayList()
        messageadapter = messageAdapter(this,msgList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageadapter

        //remember to show chats in REcycler view

        mDBref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    msgList.clear()
                    for(postSnapShot in snapshot.children){
                        val messsage = postSnapShot.getValue(message::class.java)
                        msgList.add(messsage!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        //==========================//
        sendBtn.setOnClickListener {
            val msg = messageBox.text.toString()
            val msgObj = message(msg, senderUid)
            mDBref.child("chats").child(senderRoom!!).child("messages")
                .push().setValue(msgObj).addOnSuccessListener {
                    mDBref.child("chats").child(receiverRoom!!).child("messages")
                        .push().setValue(msgObj)
            }
            messageBox.setText("")
        }
    }
}