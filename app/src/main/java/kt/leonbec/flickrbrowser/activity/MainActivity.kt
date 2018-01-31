package kt.leonbec.flickrbrowser.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import kt.leonbec.flickrbrowser.R
import kt.leonbec.flickrbrowser.adapter.PhotoListAdapter
import kt.leonbec.flickrbrowser.asyncTask.GetJsonData
import kt.leonbec.flickrbrowser.asyncTask.ParseJsonData
import kt.leonbec.flickrbrowser.data.Photo
import kt.leonbec.flickrbrowser.util.FLICKR_QUERY
import kt.leonbec.flickrbrowser.util.PHOTO_TRANSFER
import kt.leonbec.flickrbrowser.util.createUrl

class MainActivity : BaseActivity(), GetJsonData.Listener, ParseJsonData.Listener {

    private val photoListAdapter = PhotoListAdapter(mutableListOf()) {
        with(Intent(this, PhotoDetailActivity::class.java)) {
            putExtra(PHOTO_TRANSFER, it)
            startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        with(rv_photo_list) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = photoListAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//        sharedPref.edit().clear().apply()
        val query = sharedPref.getString(FLICKR_QUERY, "")

        val url = createUrl(
            "https://api.flickr.com/services/feeds/photos_public.gne?",
            query, true
        )
        val getJsonData = GetJsonData(this)
        getJsonData.execute(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.mi_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onGetJsonDataComplete(jsonData: String?) {
        if (jsonData != null) {
            ParseJsonData(this).apply { execute(jsonData) }
        }
    }

    override fun onParseDataDataComplete(mlPhoto: MutableList<Photo>) {
        if (mlPhoto.isEmpty())
            Toast.makeText(this, "Sorry, no photo matches your search", Toast.LENGTH_LONG).show()
        else
            photoListAdapter.loadData(mlPhoto)
    }
}
