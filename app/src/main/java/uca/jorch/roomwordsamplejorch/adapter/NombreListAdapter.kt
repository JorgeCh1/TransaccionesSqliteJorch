package uca.jorch.roomwordsamplejorch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uca.jorch.roomwordsamplejorch.Nombre
import uca.jorch.roomwordsamplejorch.R

class NombreListAdapter : ListAdapter<Nombre, NombreListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Nombre>() {
        override fun areItemsTheSame(oldItem: Nombre, newItem: Nombre): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Nombre, newItem: Nombre): Boolean {
            return oldItem.word == newItem.word
        }
    }
}