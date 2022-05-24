package com.example.chatify
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList
class messageAdapter(val context : Context, val messageList : ArrayList<message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item_receive = 1
    val item_sent = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receivelayout, parent,false)
            return receiveViewHolder(view)
        }else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.sentlayout, parent,false)
            return sendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currMsg = messageList[position]
        if(holder.javaClass == sendViewHolder::class.java){
            val viewHolder = holder as sendViewHolder
            holder.sendMsg.text = currMsg.message
        }else{
            val viewHolder = holder as receiveViewHolder
            holder.receivedMsg.text = currMsg.message
        }
    }
    override fun getItemViewType(position: Int): Int {
        val currmsg = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currmsg.idSender)){
            return item_sent
        }else{
            return item_receive
        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
    class sendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sendMsg  = itemView.findViewById<TextView>(R.id.senderMsg)
    }
    class receiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMsg = itemView.findViewById<TextView>(R.id.receiverMsg)
    }
}