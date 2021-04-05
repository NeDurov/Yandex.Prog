package com.example.yandex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(var context: Context, var stock: ArrayList<Stock>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var name1: TextView = row?.findViewById(R.id.name1) as TextView
        var dis: TextView = row?.findViewById(R.id.dis) as TextView
        var cost: TextView = row?.findViewById(R.id.cost) as TextView

    }

    override fun getCount(): Int {
        return stock.count()
    }

    override fun getItem(position: Int): Any {
        return stock.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_list_custom, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var stock: Stock = getItem(position) as Stock
        viewHolder.name1.text = stock.name
        viewHolder.dis.text = stock.description
        viewHolder.cost.text = stock.price.toString()

        return view as View
    }
}