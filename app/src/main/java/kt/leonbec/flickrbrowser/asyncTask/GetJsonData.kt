package kt.leonbec.flickrbrowser.asyncTask

import android.os.AsyncTask
import android.util.Log
import java.net.URL

/** Created by leonbec on 2018/1/29.*/
class GetJsonData(private val listener: Listener) : AsyncTask<String, Void, String?>() {

    companion object {
        private const val TAG = "GetJsonData"
    }

    interface Listener {
        fun onGetJsonDataComplete(jsonData: String?)
    }

    override fun doInBackground(vararg params: String?): String? {
        if (params[0] == null)
            Log.e(TAG, "doInBackground: no URL specified")

        return try {
            URL(params[0]).readText()
        } catch (e: Exception) {
            cancel(true)
            e.printStackTrace()
            Log.e(TAG, "doInBackground: ${e.message}")
            null
        }
    }

    override fun onPostExecute(result: String?) {
        Log.d(TAG,"onPostExecute: result length is ${result?.length}")
        listener.onGetJsonDataComplete(result)
    }
}