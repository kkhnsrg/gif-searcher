package edu.kokhan.gifsearcher.ui.search

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import edu.kokhan.gifsearcher.R
import edu.kokhan.gifsearcher.ui.search.adapter.GifRecyclerViewAdapter
import edu.kokhan.gifsearcher.ui.search.adapter.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity(), SearchContract.View {

    private val STRAGGERED_GRID_MARGIN = 8

    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: GifRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.hide()
        presenter = SearchPresenter()
        presenter.attach(this)
        presenter.start()
    }

    override fun init() {
        retryButton.setOnClickListener {
            presenter.onLoadTrends()
            retryButton.visibility = View.GONE
        }
        presenter.onLoadTrends()
    }

    override fun fillTrendGifList(gifList: List<String>) {
        setupAdapter(gifList)
        showGifList()
    }

    private fun showGifList() {
        recyclerView.visibility = View.VISIBLE
        logoImageView.animate().alpha(0f).duration = 200
        supportActionBar?.show()
    }

    override fun fillSearchGifList(gifList: List<String>) {
        adapter.urls = gifList
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showRetryButton() {
        retryButton.visibility = View.VISIBLE
    }

    override fun hideRetryButton() {
        retryButton.visibility = View.GONE
    }

    private fun setupAdapter(list: List<String>) {
        adapter = GifRecyclerViewAdapter(this, list)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(SpaceItemDecoration(STRAGGERED_GRID_MARGIN))
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.searchBadge)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) presenter.onLoadSearchResult(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}
