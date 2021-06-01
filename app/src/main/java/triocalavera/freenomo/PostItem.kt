package triocalavera.freenomo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import triocalavera.freenomo.Model.ChatRoom
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.databinding.PostItemFragmentBinding
import triocalavera.freenomo.ui.home.CategoryDirections


class PostItem : Fragment() {

    private lateinit var viewModel: PostItemViewModel
    val args: PostItemArgs by navArgs()
    lateinit var post: Post
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: PostItemFragmentBinding
    private var permisosOk = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.post_item_fragment, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        binding = PostItemFragmentBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostItemViewModel::class.java)
        post = args.post
val id =auth.currentUser?.uid

        binding.btnPostLLamarAhora.text = "${binding.btnPostLLamarAhora.text} ${post.numero}"
        binding.categoriaPostItem.text = post.categoria
        binding.descriocionPostItem.text = post.descripcion
        binding.tituloPostItem.text = post.titulo.capitalize()
        binding.precioPostItem.text = post.precio
        binding.horaPublicadaPostItem.text = post.fecha
        binding.btnPostAbrirChat.setOnClickListener {
            if(id != post.userName){
                val action = PostItemDirections.actionPostItemToChatting(post.userName)
                Navigation.findNavController(it).navigate(action)
            }else {
                Toast.makeText(context,"No puedes enviarte chat a ti mismo ",Toast.LENGTH_LONG).show()
            }

        }
        Glide.with(this).load(post.foto)
            //  .placeholder(R.drawable.progressbar)
            // .error(R.drawable.error)
            .into(binding.imagenPost)
        binding.btnPostLLamarAhora.setOnClickListener {
            abrirLlamada()
        }
    }



    private fun checkpermison() {
        if (view?.let {
                ActivityCompat.checkSelfPermission(
                    it.context,
                    Manifest.permission.CALL_PHONE
                )
            } != PackageManager.PERMISSION_GRANTED &&
            view?.let {
                ActivityCompat.checkSelfPermission(
                    it.context,
                    Manifest.permission.CALL_PHONE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.CALL_PHONE
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        } else {
            permisosOk = true
            abrirLlamada()
        }
    }


    private fun abrirLlamada() {
        if (permisosOk) {
            val i = Intent(Intent.ACTION_CALL)
            i.data = Uri.parse("tel:${post.numero}")
            startActivity(i)
        } else
            checkpermison()
    }

}