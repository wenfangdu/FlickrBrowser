package kt.leonbec.flickrbrowser.data

import android.os.Parcel
import android.os.Parcelable

/** Created by leonbec on 2018/1/29.*/
data class Photo(
    val title: String,
    val thumbnailUrl: String,
    val photoUrl: String,
    val author: String,
    val tags: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(photoUrl)
        parcel.writeString(author)
        parcel.writeString(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}