package triocalavera.freenomo.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import triocalavera.freenomo.Model.Category
import triocalavera.freenomo.R
import triocalavera.freenomo.ui.home.CategoryDirections

class CategoryAdapter(var context: Context, var categoryList: MutableList<Category>) :
    BaseAdapter() {


    override fun getCount() = categoryList.size


    override fun getItem(position: Int) = categoryList[position]


    override fun getItemId(position: Int) = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.categoryitem, null)
        val logo = view.findViewById<ImageView>(R.id.categoryImage)
        val text = view.findViewById<TextView>(R.id.titleCategory)
        Glide.with(view.context).load(categoryList[position].foto)
            //  .placeholder(R.drawable.progressbar)
            // .error(R.drawable.error)
            .into(logo)
        text.text = categoryList[position].categoria
        val action = CategoryDirections.actionCategoryToSearchForCategory(text.text.toString())
        view.setOnClickListener { v -> Navigation.findNavController(v).navigate(action) }
        return view
    }
}