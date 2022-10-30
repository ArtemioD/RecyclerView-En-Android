package com.artemiod.recyclerviewenandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artemiod.recyclerviewenandroid.R
import com.artemiod.recyclerviewenandroid.databinding.ItemListBinding
import com.artemiod.recyclerviewenandroid.databinding.ItemListGridBinding
import com.artemiod.recyclerviewenandroid.model.Article

class ItemAdapter(private val onClick: (Article) -> Unit):
    ListAdapter<Article, ItemAdapter.ItemViewHolder>(DiffCalBack) {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemListBinding.bind(view)
        //private val binding = ItemListGridBinding.bind(view)

        private var currentArticle: Article? = null

        init {
            // Escucha View.OnClickListener al view almacenado por ArticleViewHolder.
            // Al detectar el click se ejecuta un tipo función (Article) -> Unit
            // que pasamos en su constructor de ItemAdapter
            view.setOnClickListener {
                currentArticle?.let {
                    onClick(it)
                }
            }
        }

        fun bind (article: Article) {
            currentArticle = article

            binding.articleTitle.text = article.title
            binding.articleDescription.text = article.description
            binding.featuredImage.setImageResource(article.featuredImage)
        }

    }

    // onCreateViewHolder() crea nuevas interfaces de vista para RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    // método para reemplaza el contenido de una vista con elementos de lista
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    // para optimizar la cantidad de notificaciones que se realizan y mejora el rendimiento
    // en casos donde existen grandes cantidades de ítems
    companion object DiffCalBack: DiffUtil.ItemCallback<Article>() {

        //Los métodos areItemsTheSame() y areContentsTheSame() ejecutan una comparación
        // de igualdad de la identidad y contenido entre el ítem (oldItem)
        // existente y el nuevo (newItem).

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

}