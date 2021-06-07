package triocalavera.freenomo.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import triocalavera.freenomo.Model.Mensaje
import triocalavera.freenomo.R
import java.util.*


class MensajeAdapter(var color: Int, var miNombre: String) :
    RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {
    private val mensjeMutableList = mutableListOf<Mensaje>()
    inner class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var logoIzquieda = view.findViewById<ImageView>(R.id.imgIzquierda)
        var logoDerecha = view.findViewById<ImageView>(R.id.imgDerecha)
        var cardViewUsuario = view.findViewById<CardView>(R.id.cardviewChattingright)
        var cardViewDestion = view.findViewById<CardView>(R.id.cardviewChatleft)
        var mensajeUsuario = view.findViewById<TextView>(R.id.mensajeDerecha)
        var mensajeDestion = view.findViewById<TextView>(R.id.mensajeIzquierda)
        var firstLetraUsuario = view.findViewById<TextView>(R.id.letraChatPostUsuario)
        var firstLetraDestino = view.findViewById<TextView>(R.id.letraChatPostDestino)
    }

    fun addMensaje(m: Mensaje?) {
        m?.let { mensjeMutableList.add(it) }
        notifyItemInserted(mensjeMutableList.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_derecha, parent, false)
        return MensajeViewHolder(view)
    }

    override fun getItemCount() = mensjeMutableList.size


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val rnd = Random()

        if (color == 0) {
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

        if (miNombre != mensjeMutableList[position].nombre) {
            holder.cardViewDestion.visibility=View.GONE
            holder.mensajeDestion.visibility=View.GONE
            holder.logoDerecha.setBackgroundColor(Color.argb(255, 0, 208, 0))
            holder.firstLetraUsuario.text = miNombre[0].toString().capitalize()
            Toast.makeText(holder.itemView.context,"${mensjeMutableList[position].nombre}",Toast.LENGTH_LONG).show()
holder.mensajeUsuario.text=mensjeMutableList[position].mensaje

        } else {
            holder.cardViewUsuario.visibility = View.GONE
            holder.mensajeUsuario.visibility = View.GONE
            holder.logoIzquieda.setBackgroundColor(color)
            holder.mensajeUsuario.text = mensjeMutableList[position].mensaje
            holder.firstLetraDestino.text=mensjeMutableList[position].nombre[0].toString().capitalize()

        }
    }

}
