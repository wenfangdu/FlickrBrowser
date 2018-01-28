package kt.leonbec.flickrbrowser.asyncTask

import android.os.AsyncTask
import android.util.Log
import kt.leonbec.flickrbrowser.data.Photo
import org.json.JSONObject

/** Created by leonbec on 2018/1/29.*/
class ParseJsonData(private val listener: Listener) :
    AsyncTask<String, Void, MutableList<Photo>>() {

    companion object {
        private const val TAG = "ParseJsonData"
    }

    interface Listener {
        fun onParseDataDataComplete(mlPhoto: MutableList<Photo>)
    }

    override fun doInBackground(vararg params: String?): MutableList<Photo> {
        val mlPhoto = mutableListOf<Photo>()
        try {
            val jsonObj = JSONObject(params[0])
            val items = jsonObj.getJSONArray("items")

            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val title = item.getString("title")

                val media = item.getJSONObject("media")
                val thumbnailUrl = media.getString("m")
                val photoUrl = thumbnailUrl.replaceFirst("_m.jpg", "_b.jpg")

                val author = item.getString("author")
                val tags = item.getString("tags")

                val photo = Photo(title, thumbnailUrl, photoUrl, author, tags)
                mlPhoto.add(photo)
            }
        } catch (e: Exception) {

            cancel(true)
            e.printStackTrace()
            Log.e(TAG, "doInBackground: ${e.message}")
        }
        return mlPhoto
    }

    override fun onPostExecute(result: MutableList<Photo>) {
        Log.d(TAG,"onPostExecute: result size is ${result.size}")
        listener.onParseDataDataComplete(result)
    }
}