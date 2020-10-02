package hk.collaction.timber.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import hk.collaction.timber.api.model.Tree
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class TreeListResponseModel(
    @SerializedName("data") var data: Data? = null
) : BaseResponseModel() {
    @Parcelize
    data class Data(
        @SerializedName("trees") var trees: ArrayList<Tree>? = null
    ) : Parcelable
}