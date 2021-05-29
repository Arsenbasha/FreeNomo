package triocalavera.freenomo.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.R

class PostAdapter(var context: Context, var postMutableList: ArrayList<Post>) : BaseAdapter() {
    override fun getCount() = postMutableList.size


    override fun getItem(position: Int) = postMutableList.get(position)


    override fun getItemId(position: Int) = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.post_item, null)
        val logo = view.findViewById<ImageView>(R.id.perfilPost)
        var text = view.findViewById<TextView>(R.id.titlePost)
        Glide.with(view.context).load(postMutableList[position].foto)
            //  .placeholder(R.drawable.progressbar)
            // .error(R.drawable.error)
            .into(logo)
        text.text = postMutableList[position].titulo
        return view


    }
}