package com.artemiod.recyclerviewenandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artemiod.recyclerviewenandroid.R
import com.artemiod.recyclerviewenandroid.adapter.ItemAdapter
import com.artemiod.recyclerviewenandroid.data.Datasource
import com.artemiod.recyclerviewenandroid.databinding.ActivityMainBinding
import com.artemiod.recyclerviewenandroid.model.Article

// Tutorial: https://www.develou.com/recyclerview-en-android/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView1()   // LinearLayout
        //itemRecyclerView2()   // GridLayout
        //itemRecyclerView3()   // GridLayout con un item que ocupe un span completo de la grilla
    }

    private fun itemRecyclerView3() {
        // tambien modificar en el adapter el item_list y binding
        val articleList: RecyclerView = binding.recyclerView // (1)
        val articleAdapter = ItemAdapter { onItemClick(it) } // (2)
        // (3)
        val gridLayoutManager = GridLayoutManager(this, 2)
        // deseamos que el primer elemento ocupe 2 espacios
        // el resto uno
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when(position) {
                0 -> 2
                else -> 1
            }
        }
        val gutter = resources.getDimensionPixelSize(R.dimen.grid_gutter)
        articleList.apply {
            adapter = articleAdapter
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addItemDecoration(GutterDecoration(gutter))
        }

        articleAdapter.submitList(Datasource().getArticles()) // (4)
    }

    private fun itemRecyclerView2() {
        // tambien modificar en el adapter el item_list y binding
        val articleList: RecyclerView = binding.recyclerView // (1)
        val articleAdapter = ItemAdapter { onItemClick(it) } // (2)
        // (3)
        val gutter = resources.getDimensionPixelSize(R.dimen.grid_gutter)
        articleList.apply {
            adapter = articleAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addItemDecoration(GutterDecoration(gutter))
        }
        articleAdapter.submitList(Datasource().getArticles()) // (4)

    }

    private fun itemRecyclerView1() {
        val articleList: RecyclerView = binding.recyclerView // (1)
        val articleAdapter = ItemAdapter { onItemClick(it) } // (2)
        articleList.adapter = articleAdapter // (3)

        // También es posible asignar el objeto LayouManager en tiempo de ejecución
        //articleList.layoutManager = LinearLayoutManager(this) // (3.1)

        /* Si la lista que deseas mostrar tiene una cantidad de ítems fija,
        puedes usar el método setHasFixedSize() con true para acotar el comportamiento
        del RecyclerView y mejorar el rendimiento de dibujado */
        articleList.setHasFixedSize(true) // (3.2)

        // para enterar al adaptador de una nueva actualización de los datos
        articleAdapter.submitList(Datasource().getArticles()) // (4)
    }

    private fun onItemClick(article: Article) {
        Toast.makeText(this, article.title, Toast.LENGTH_SHORT).show()
    }
}