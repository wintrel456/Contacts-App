package com.gmail.l2t45s7e9.library.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.l2t45s7e9.library.R

class AddressSearchAdapter(private val onItemClick: OnItemClickListener) :
    ListAdapter<String, AddressSearchAdapter.ItemViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClicked(string: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.address_search_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (getItem(position) != null) {
            holder.bind(getItem(position), onItemClick)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressSearchResult: TextView = itemView.findViewById(R.id.addressSearchResult)
        fun bind(item: String, onItemClick: OnItemClickListener) = with(itemView) {
            addressSearchResult.text = item
            setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick.onItemClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
