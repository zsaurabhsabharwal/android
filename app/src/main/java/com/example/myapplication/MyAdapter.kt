package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter (private val cryptoList : ArrayList<Result>, private val listener : Listener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(result: Result)
    }

    private val colors: Array<String> =
        arrayOf("#7E57C2", "#42A5F5", "#26C6DA", "#66BB6A", "#FFEE58", "#FF7043", "#EC407A", "#d32f2f")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cryptoList[position], listener, colors, position)
    }

    override fun getItemCount(): Int = cryptoList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(result: Result, listener: Listener, colors: Array<String>, position: Int) {

            itemView.setOnClickListener { listener.onItemClick(result) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.albumId.text =  result.albumId.toString()
            itemView.Id.text = result.id.toString()
            itemView.title.text = result.title
            itemView.url.text = result.url
            itemView.thumbnailUrl.text = result.thumbnailUrl

        }
    }
}