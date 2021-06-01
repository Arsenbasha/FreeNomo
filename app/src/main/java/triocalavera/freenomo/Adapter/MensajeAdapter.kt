package triocalavera.freenomo.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import triocalavera.freenomo.Model.Mensaje
import triocalavera.freenomo.R
import java.util.*


class MensajeAdapter(private val mensjeMutableList: MutableList<Mensaje>) :
    RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    inner class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var logo = view.findViewById<ImageView>(R.id.imageViewChatReycrcle)
        var userName = view.findViewById<TextView>(R.id.nombreUsuariochat)
        var firstLetra = view.findViewById<TextView>(R.id.letraChatPost)
    }

    fun addMensaje(m: Mensaje?) {
        m?.let { mensjeMutableList.add(it) }
        notifyItemInserted(mensjeMutableList.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycler_view_item, parent, false)
        return MensajeViewHolder(view)
    }

    override fun getItemCount() = mensjeMutableList.size

    private var iscolor: Boolean=false
    private var color=0
    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val rnd = Random()
        if (!iscolor) {
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            iscolor = true
        }
//        holder.firstLetra.text = mensjeMutableList[position].nombre[0].toString().capitalize()
        holder.userName.text = mensjeMutableList[position].mensaje
        holder.logo.setBackgroundColor(color);

    }

}
