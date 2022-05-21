package com.example.chatify

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class userAdapter(val context : Context, val userList : ArrayList<user>) : RecyclerView.Adapter<userAdapter.userViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currUser = userList[position]
        holder.txtName.text = currUser.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, chatActivity::class.java)
            intent.putExtra("name", currUser.name)
            intent.putExtra("uid", currUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.text_name)

    }
}