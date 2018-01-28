package kt.leonbec.flickrbrowser.util

import android.net.Uri

/** Created by leonbec on 2018/1/29.*/
fun createUrl(url: String, tags: String, matchAll: Boolean): String {
    return Uri.parse(url).buildUpon()
        .appendQueryParameter("tags", tags)
        .appendQueryParameter("tagmode", if (matchAll) "all" else "any")
        .appendQueryParameter("format", "json")
        .appendQueryParameter("nojsoncallback", "1")
        .toString()
}