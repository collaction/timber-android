package hk.collaction.timber.api.request

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Himphen on 23/8/2017.
 */
@Parcelize
class LikeTreeRequestModel(
    @SerializedName("tree_id") var treeId: Int? = null
) : BaseRequestModel()