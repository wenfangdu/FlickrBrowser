package kt.leonbec.flickrbrowser.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kt.leonbec.flickrbrowser.R
import kt.leonbec.flickrbrowser.data.Photo

/** Created by leonbec on 2018/1/29.*/
class PhotoListAdapter(
    private var mlPhoto: MutableList<Photo>,
    private val onClick: (Photo) -> Unit
) :
    RecyclerView.Adapter<PhotoListAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_photo, parent, false)
        return VH(v, onClick)
    }

    override fun getItemCount(): Int {
        return mlPhoto.size
    }

    override fun onBindViewHolder(holder: VH?, position: Int) {
        holder?.bindPhoto(mlPhoto[position])
    }

    fun loadData(data: MutableList<Photo>) {
        mlPhoto = data
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo {
        return mlPhoto[position]
    }

    class VH(v: View, private val onClick: (Photo) -> Unit) : RecyclerView.ViewHolder(v) {
        private val ivThumbnail = v.findViewById<ImageView>(R.id.iv_thumbnail)
        private val tvTitle = v.findViewById<TextView>(R.id.tv_title)

        fun bindPhoto(photo: Photo) {
            Picasso.with(itemView.context)
                .load(photo.thumbnailUrl)
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivThumbnail)

            tvTitle.text = photo.title
            //注意，这里设置onClickListener 会导致在搜索框用外接键盘enter时，焦点到view上
            itemView.setOnClickListener { onClick(photo) }
        }
    }
}