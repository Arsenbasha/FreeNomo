package triocalavera.freenomo.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import triocalavera.freenomo.Model.Chat
import triocalavera.freenomo.R
import java.util.*

class ChatAdapter(private val chatsMutableList: MutableList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.MensajeViewHolder>() {

    inner class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var logo = view.findViewById<ImageView>(R.id.imageViewChatReycrcle)
        var userName = view.findViewById<TextView>(R.id.nombreUsuariochat)
        var firstLetra =view.findViewById<TextView>(R.id.letraChatPost)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycler_view_item, parent, false)
        return MensajeViewHolder(view)
    }

    override fun getItemCount() = chatsMutableList.size

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

       holder.firstLetra.text =chatsMutableList[position].username[0].toString().capitalize()
        holder.userName.text = chatsMutableList[position].username
        holder.logo.setBackgroundColor(color);

    }

}
