package triocalavera.freenomo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import triocalavera.freenomo.Adapter.ChatAdapter
import triocalavera.freenomo.Model.Chat
import triocalavera.freenomo.databinding.ChatFragmentRecryclerviewBinding


class ChatRecyrcleView : Fragment() {

    private lateinit var adapter: ChatAdapter
    private lateinit var recyrcleViewModel: ChatRecyrcleViewModel
    private lateinit var binding: ChatFragmentRecryclerviewBinding
    private var lista = mutableListOf<Chat>()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recyrcleViewModel = ViewModelProvider(this).get(ChatRecyrcleViewModel::class.java)
        val view = inflater.inflate(R.layout.chat_fragment_recryclerview, container, false)
        binding = ChatFragmentRecryclerviewBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            view.let {
                Snackbar.make(
                    it.rootView,
                    "Debes iniciar Sesion para ver tus Chats",
                    Snackbar.LENGTH_LONG
                )
            }
            findNavController().navigate(R.id.login)
        } else {
            database =
                FirebaseDatabase.getInstance().reference.child("chat").child(auth.currentUser!!.uid)
lista.clear()
            database.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (c: DataSnapshot in dataSnapshot.children) {
                            if (c.child("nombre").value.toString() != "null") {
                                lista.add(
                                    Chat(
                                        "",
                                        "",
                                        c.child("nombre").value.toString(),
                                        c.child("uidDestino").value.toString()
                                    )
                                )
                            }
                        }
                        if (lista.isNotEmpty()) {
                            binding.nohayChat.visibility = View.GONE
                            lista.forEach { v ->
                            }

                            adapter = ChatAdapter(
                                lista
                            )
                            val dividerItemDecoration = DividerItemDecoration(
                                binding.recyclerViewChats.context,
                                LinearLayoutManager.VERTICAL
                            )
                            binding.recyclerViewChats.addItemDecoration(dividerItemDecoration)
                            val recycleView =
                                view.findViewById<RecyclerView>(R.id.recycler_viewChats)
                            recycleView.adapter = adapter
                            recycleView.layoutManager = LinearLayoutManager(context)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }


}


