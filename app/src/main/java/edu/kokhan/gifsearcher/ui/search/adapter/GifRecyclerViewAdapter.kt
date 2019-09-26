package edu.kokhan.gifsearcher.ui.search.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.kokhan.gifsearcher.R
import kotlinx.android.synthetic.main.view_gif.view.*

class GifRecyclerViewAdapter(
    private val context: Context,
    var urls: List<String>
) : RecyclerView.Adapter<GifRecyclerViewAdapter.SpecialViewHolder>() {

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialViewHolder {
        return SpecialViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_gif,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialViewHolder, position: Int) {
        Glide.with(context)
            .load(urls[position])
            .override(500)
            .placeholder(R.drawable.wait)
            .error(R.drawable.wrong)
            .into(holder.imgGif)
    }

    class SpecialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgGif: ImageView = view.imageGif
    }
}
