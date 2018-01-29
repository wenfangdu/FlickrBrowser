package kt.leonbec.flickrbrowser.activity

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_detail.*
import kt.leonbec.flickrbrowser.R
import kt.leonbec.flickrbrowser.data.Photo
import kt.leonbec.flickrbrowser.util.PHOTO_TRANSFER

class PhotoDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        activateToolbar(true)

        val photo = intent.getParcelableExtra<Photo>(PHOTO_TRANSFER)
        tv_title.text = photo.title

        Picasso.with(this)
            .load(photo.photoUrl)
            .error(R.drawable.ic_broken_image)
            .placeholder(R.drawable.ic_placeholder)
            .into(iv_photo)

        tv_author.text = getString(R.string.photo_author, photo.author)
        tv_tags.text = getString(R.string.photo_tags, photo.tags)
    }

}
