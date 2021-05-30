package triocalavera.freenomo.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import triocalavera.freenomo.R

class CategoryAdapter(var context: Context, var categoryList: ArrayList<String>) : BaseAdapter() {


    override fun getCount() = categoryList.size


    override fun getItem(position: Int) = categoryList[position]


    override fun getItemId(position: Int) = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.categoryitem, null)
        val logo = view.findViewById<ImageView>(R.id.categoryImage)
        var text = view.findViewById<TextView>(R.id.titleCategory)
        text.text = categoryList[position]
        view.setOnClickListener { Toast.makeText(context ,"${text.text}" , Toast.LENGTH_SHORT).show() }
        return view


    }
}