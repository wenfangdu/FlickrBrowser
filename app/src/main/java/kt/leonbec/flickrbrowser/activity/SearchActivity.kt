package kt.leonbec.flickrbrowser.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.SearchView
import kt.leonbec.flickrbrowser.R
import kt.leonbec.flickrbrowser.util.FLICKR_QUERY

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolbar(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener { finish();false }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPref.edit().putString(FLICKR_QUERY, query).apply()
        searchView?.clearFocus()
        finish()

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
