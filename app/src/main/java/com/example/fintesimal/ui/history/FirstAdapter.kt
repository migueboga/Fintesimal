package com.example.fintesimal.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fintesimal.R
import com.example.fintesimal.data.db.entities.Address
import kotlinx.android.synthetic.main.item_history.view.*

class FirstAdapter(private val interaction: Interaction? = null):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Address>() {

        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FirstViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_history,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Address>) {
        differ.submitList(list)
    }

    class FirstViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Address) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(item)
            }
            itemView.title_item_history.text = item.visited.toString()
            itemView.title_item_address.text = item.streetName
            itemView.title_item_zone.text = item.suburb
            //itemView.icon_item_history.colorFilter = ContextCompat.getColor(itemView.context, R.color.backgroundGreen)
            if (item.visited) {
                itemView.title_item_history.text = "Visitada"
                itemView.title_item_history.setTextColor(context.getColor(R.color.backgroundGreen))
                itemView.icon_item_history.setColorFilter(ContextCompat.getColor(context, R.color.backgroundGreen), android.graphics.PorterDuff.Mode.SRC_IN)
            } else {
                itemView.title_item_history.text = "Pendiente"
                itemView.title_item_history.setTextColor(context.getColor(R.color.gray_text_color))
                itemView.icon_item_history.setColorFilter(ContextCompat.getColor(context, R.color.gray_text_color), android.graphics.PorterDuff.Mode.SRC_IN)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(item: Address)
    }


}