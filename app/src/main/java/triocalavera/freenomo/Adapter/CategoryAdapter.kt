package triocalavera.freenomo.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import triocalavera.freenomo.R
import triocalavera.freenomo.ui.home.categoryDirections

class CategoryAdapter(var context: Context, var categoryList: ArrayList<String>) : BaseAdapter() {


    override fun getCount() = categoryList.size


    override fun getItem(position: Int) = categoryList[position]


    override fun getItemId(position: Int) = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.categoryitem, null)
        val logo = view.findViewById<ImageView>(R.id.categoryImage)
        var text = view.findViewById<TextView>(R.id.titleCategory)
        text.text = categoryList[position]
        val action = categoryDirections.actionCategoryToSearchForCategory(text.text.toString())
        view.setOnClickListener {v -> Navigation.findNavController(v).navigate(action) }
        return view
    }
}