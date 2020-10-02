package hk.collaction.timber.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponseModel(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null
) : Parcelable