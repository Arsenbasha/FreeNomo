package triocalavera.freenomo.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.Adapter.CategoryAdapter
import triocalavera.freenomo.Adapter.PostAdapter
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.databinding.FragmentHomeBinding

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var _binding: FragmentHomeBinding
    private var post = mutableListOf<Post>()

    @SuppressLint("StaticFieldLeak")
    private lateinit var _activity: FragmentActivity
    fun init(
        bindig: FragmentHomeBinding,
        requireActivity: FragmentActivity
    ) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        _binding = bindig
        _activity = requireActivity

    }

     fun obtenerPost() {
        database.child("post").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    post.clear()
                    for (c: DataSnapshot in dataSnapshot.children) {
                            post.add(
                                Post(
                                    c.child("uuid").value.toString(),
                                    c.child("titulo").value.toString(),
                                    c.child("categoria").value.toString(),
                                    c.child("foto").value.toString(),
                                    c.child("descripcion").value.toString(),
                                    c.child("telefono").value.toString(),
                                    c.child("precio").value.toString()
                                )
                            )
                    }
                    getPost()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d("INFO", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

   private fun getPost() {
        val grid = _binding.grid

        var categoryPost = mutableListOf<Post>()
      post.forEach {
          if (it.categoria.equals("Programación",false)){
              categoryPost.add(it)
          }
      }
       val categoryAdapter = PostAdapter(_binding.root.context, categoryPost)
        _binding.progresBarHome.visibility = View.INVISIBLE
        grid.adapter = categoryAdapter
    }


}