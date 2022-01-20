package hk.collaction.timber.api.request

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class TreeListRequestModel(
    @SerializedName("type") var type: String? = null,
    @SerializedName("lang") var lang: String? = null,
    @SerializedName("tree_id") var treeId: Int? = null
) : BaseRequestModel()