package hk.collaction.timber.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TreePhotoPath(
    @SerializedName("filename") val filename: String? = null,
    @SerializedName("original") val original: String? = null
) : Parcelable