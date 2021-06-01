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
    private lateinit var uid: String
    private lateinit var binding: FragmentChattingBinding
    private var nombredetino: String = ""
    private var minombre: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chatting, container, false)

        binding = FragmentChattingBinding.bind(view)
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val id = auth.currentUser!!.uid
        uid = args.uid

        database = FirebaseDatabase.getInstance().reference
        databasemensajesenvio = database.child("chat").child(id).child(uid)
            .child("mensajes")
        databasemensajesrecibidos = database.child("chat").child(uid).child(id)
            .child("mensajes")
        database.child("Users").child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    minombre = dataSnapshot.child("nombreCompleto").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {}

            })
        if (uid != null || !(uid == "")) {
            databasemensajesenvio.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        database.child("Users").child(uid).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                nombredetino = dataSnapshot.child("nombreCompleto").value.toString()
                                val map: MutableMap<String, Any> = HashMap()
                                map["nombre"] = nombredetino
                                database.child("chat").child(id).child(uid).setValue(map)
                                database.child("Users").child(id)
                                    .addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val map: MutableMap<String, Any> = HashMap()
                                            map["nombre"] = minombre
                                            binding.nombre.text = nombredetino
                                            database.child("chat").child(uid).child(id)
                                                .setValue(map)
                                        }
                                        override fun onCancelled(error: DatabaseError) {
                                        }
                                    })
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                    }else{
                        database.child("Users").child(uid)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    nombredetino = dataSnapshot.child("nombreCompleto").value.toString()

                                    binding.nombre.text = nombredetino}

                                override fun onCancelled(error: DatabaseError) {}

                            })
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.d("INFO", "loadPost:onCancelled", databaseError.toException())
                }
            })
        }else{Toast.makeText(context,"Es null",Toast.LENGTH_SHORT).show()}
    }
    private lateinit var adapter: MensajeAdapter
    private var lista = mutableListOf<Mensaje>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MensajeAdapter(
            lista
        )

        val recycleView = view.findViewById<RecyclerView>(R.id.rvMensajes)
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(context)
        binding.btnEnviar.setOnClickListener {

            databasemensajesenvio.push()
                .setValue(Mensaje(nombredetino, binding.txtMensaje.text.toString(), "000"))
            Toast.makeText(context,"nombre del destino$nombredetino  $uid",Toast.LENGTH_SHORT).show()
            databasemensajesrecibidos.push()
                .setValue(Mensaje(minombre, binding.txtMensaje.text.toString(), "000"))
            Toast.makeText(context," mi nombre$minombre $id",Toast.LENGTH_SHORT).show()
            binding.txtMensaje.text.clear()
        }
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })

        databasemensajesenvio.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val m: Mensaje? = dataSnapshot.getValue(Mensaje::class.java)
                adapter.addMensaje(m)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun setScrollbar() {
        binding.rvMensajes.scrollToPosition(adapter.itemCount - 1)
    }
}