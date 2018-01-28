package kt.leonbec.flickrbrowser.activity

import android.os.Bundle
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
import kt.leonbec.flickrbrowser.util.createUrl

class MainActivity : BaseActivity(), GetJsonData.Listener, ParseJsonData.Listener {

    private val photoListAdapter = PhotoListAdapter(mutableListOf()) {
        Toast.makeText(this, "You tapped it, bro", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        rv_photo_list.layoutManager = LinearLayoutManager(this)
        rv_photo_list.adapter = photoListAdapter
    }

    override fun onResume() {
        super.onResume()

        val url = createUrl(
            "https://api.flickr.com/services/feeds/photos_public.gne?",
            "kotlin", true
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onGetJsonDataComplete(jsonData: String?) {
        if (jsonData != null) {
            val parseJsonData = ParseJsonData(this)
            parseJsonData.execute(jsonData)
        }
    }

    override fun onParseDataDataComplete(mlPhoto: MutableList<Photo>) {
        photoListAdapter.loadData(mlPhoto)
    }
}
