package kt.leonbec.flickrbrowser.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kt.leonbec.flickrbrowser.R

/** Created by leonbec on 2018/1/29.*/
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun activateToolbar(showHomeAsUp: Boolean) {
        with(findViewById<Toolbar>(R.id.toolbar)) {
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
    }
}