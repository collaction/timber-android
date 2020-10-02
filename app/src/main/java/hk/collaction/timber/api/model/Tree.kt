package hk.collaction.timber.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tree(
    @SerializedName("id") val id: String? = null,
    @SerializedName("ref_no") val refNo: String? = null,
    @SerializedName("ovt_no") val ovtNo: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lng") val lng: Double? = null,
    @SerializedName("district") val district: String? = null,
    @SerializedName("condition") val condition: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("photo_path") val photoPath: TreePhotoPath? = null,
) : Parcelable {
    override fun toString(): String {
        return "Tree{" +
                "id=" + id +
                ", refNo='" + refNo + '\'' +
                '}'
    }
}