package triocalavera.freenomo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import triocalavera.freenomo.Adapter.ChatAdapter
import triocalavera.freenomo.Model.Chat
import triocalavera.freenomo.databinding.ChatFragmentRecryclerviewBinding

class ChatRecyrcleView : Fragment() {

  private lateinit var adapter:ChatAdapter
    private lateinit var recyrcleViewModel: ChatRecyrcleViewModel
    private lateinit var binding: ChatFragmentRecryclerviewBinding
    private var lista = mutableListOf<Chat>()
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
        lista.add(Chat("df","fv","Leo"))
        adapter= ChatAdapter(
            lista
        )
        val recycleView =view.findViewById<RecyclerView>(R.id.recycler_viewChats)
        recycleView.adapter=adapter
        recycleView.layoutManager= LinearLayoutManager(context)

    }


}