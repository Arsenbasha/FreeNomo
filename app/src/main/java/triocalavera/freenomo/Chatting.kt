package triocalavera.freenomo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import triocalavera.freenomo.Adapter.MensajeAdapter
import triocalavera.freenomo.Model.Mensaje
import triocalavera.freenomo.databinding.FragmentChattingBinding

class Chatting : Fragment() {

    val args: ChattingArgs by navArgs()
    private lateinit var database: DatabaseReference
    private lateinit var databasemensajesenvio: DatabaseReference
    private lateinit var databasemensajesrecibidos: DatabaseReference

    private lateinit var auth: FirebaseAuth
    private lateinit var Destinationuid: String
    private lateinit var binding: FragmentChattingBinding
    private lateinit var nombredetino: String
    private lateinit var minombre: String
    private lateinit var adapter: MensajeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chatting, container, false)
        binding = FragmentChattingBinding.bind(view)

        return view
    }


    private var myUid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        myUid = auth.currentUser!!.uid
        Destinationuid = args.uid
        database = FirebaseDatabase.getInstance().reference
        databasemensajesenvio = database.child("chat").child(myUid).child(Destinationuid)
            .child("mensajes")
        databasemensajesrecibidos = database.child("chat").child(Destinationuid).child(myUid)
            .child("mensajes")
        database.child("Users").child(myUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    minombre = dataSnapshot.child("nombreCompleto").value.toString()
                    adapter = MensajeAdapter(
                        args.color, minombre
                    )
                    adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            super.onItemRangeInserted(positionStart, itemCount)
                            setScrollbar()
                        }
                    })
                    val recycleView = view!!.findViewById<RecyclerView>(R.id.rvMensajes)
                    recycleView.adapter = adapter
                    recycleView.layoutManager = LinearLayoutManager(context)
                    databasemensajesenvio.addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                            val m: Mensaje? = dataSnapshot.getValue(Mensaje::class.java)
                            if (::adapter.isInitialized)
                                adapter.let { it.addMensaje(m) }
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        databasemensajesenvio = database.child("chat").child(myUid).child(Destinationuid)
            .child("mensajes")
        databasemensajesrecibidos = database.child("chat").child(Destinationuid).child(myUid)
            .child("mensajes")

        primerChat()

    }



    private fun primerChat() {
        databasemensajesenvio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    database.child("Users").child(Destinationuid)
                        .addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                nombredetino =
                                    dataSnapshot.child("nombreCompleto").value.toString()
                                val map: MutableMap<String, Any> = HashMap()
                                map["nombre"] = nombredetino
                                map["uidDestino"] = Destinationuid
                                database.child("chat").child(myUid).child(Destinationuid)
                                    .setValue(map)
                                database.child("Users").child(myUid)
                                    .addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val map: MutableMap<String, Any> = HashMap()
                                            map["nombre"] = minombre
                                            map["uidDestino"] = myUid

                                            database.child("chat").child(Destinationuid)
                                                .child(myUid)
                                                .setValue(map)
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                        }
                                    })
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                } else {
                    database.child("Users").child(Destinationuid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                nombredetino = dataSnapshot.child("nombreCompleto").value.toString()
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                }
                binding.nombre.text = nombredetino
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d("INFO", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nombredetino = args.nombre
        val color = args.color
        binding.fotoPerfil.setBackgroundColor(color)
        Toast.makeText(context,"$nombredetino",Toast.LENGTH_SHORT).show()
        binding.nombre.text = nombredetino
        binding.Letra.text
      binding.Letra.text = nombredetino[0].toString().capitalize()
        binding.btnEnviar.setOnClickListener {
            databasemensajesenvio.push()
                .setValue(Mensaje(minombre, binding.txtMensaje.text.toString(), "000"))
            databasemensajesrecibidos.push()
                .setValue(Mensaje(minombre, binding.txtMensaje.text.toString(), "000"))
            binding.txtMensaje.text.clear()
        }
    }

    private fun setScrollbar() {
        binding.rvMensajes.scrollToPosition(adapter.itemCount - 1)
    }
}