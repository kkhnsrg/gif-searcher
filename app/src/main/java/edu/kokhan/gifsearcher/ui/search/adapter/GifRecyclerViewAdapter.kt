package edu.kokhan.gifsearcher.ui.search.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.kokhan.gifsearcher.R
import edu.kokhan.gifsearcher.data.GifInfo
import kotlinx.android.synthetic.main.view_gif.view.*


class GifRecyclerViewAdapter(
    private val context: Context,
    var gifs: List<GifInfo>
) : RecyclerView.Adapter<GifRecyclerViewAdapter.SpecialViewHolder>() {

    var query: String? = null

    override fun getItemCount(): Int {
        return gifs.size
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
        holder.bind(gifs[position], context)
    }

    class SpecialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_SIZE = 2
        val imgGif: ImageView = view.imageGif

        fun bind(gif: GifInfo, context: Context) {
            imgGif.layoutParams.height = gif.height.toInt() * IMAGE_SIZE
            imgGif.layoutParams.width = gif.width.toInt() * IMAGE_SIZE
            Glide.with(context)
                .load(gif.url)
                .placeholder(R.drawable.wait)
                .error(R.drawable.wrong)
                .into(imgGif)
        }
    }
}
