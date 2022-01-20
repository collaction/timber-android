package hk.collaction.timber.ui.tree

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hk.collaction.timber.R
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.databinding.FragmentTreeBinding
import hk.collaction.timber.ui.base.BaseFragment
import hk.collaction.timber.utils.GeneralUtils.getAppVersionName
import hk.collaction.timber.utils.Utils.ARG_TREE_ID
import hk.collaction.timber.utils.Utils.getCurrentLanguage
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Himphen on 23/8/2017.
 */
class TreeFragment : BaseFragment<FragmentTreeBinding>() {

    private val vm: TreeViewModel by viewModel()
    private var tree: Tree? = null

    init {
        lifecycleScope.launchWhenCreated {
            vm.treeLiveData.observe(this@TreeFragment, {
                tree = it
                onGetTree()
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewBinding = viewBinding!!

        viewBinding.directionButton.setOnClickListener { onClickDirection() }
        viewBinding.reportButton.setOnClickListener { onClickReport() }

        viewBinding.rvlist.layoutManager = LinearLayoutManager(requireContext())

        arguments?.getString(ARG_TREE_ID)?.let { treeId ->
            vm.getTree(getCurrentLanguage(requireContext()), treeId)
        }
    }

    private fun onGetTree() {
        val lat = tree?.lat
        val lng = tree?.lng
        val name = tree?.name
        val address = tree?.address

        require(lat is Double)
        require(lng is Double)
        require(name is String)
        require(address is String)

        val latLng = LatLng(lat, lng)
        val cp = CameraPosition.Builder()
            .target(latLng)
            .zoom(15f)
            .build()
        val fragment = SupportMapFragment.newInstance(GoogleMapOptions().camera(cp))
        childFragmentManager.beginTransaction().replace(R.id.mapFragment, fragment).commit()
        fragment.getMapAsync { googleMap ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(address)
            )

//				googleMap.getUiSettings().setScrollGesturesEnabled(false);
        }

        val list = mutableListOf<TreeInfoItem>()
        resources.getStringArray(R.array.tree_info_array).forEachIndexed { index, s ->
            list.add(
                TreeInfoItem(
                    s,
                    getData(index)
                )
            )
        }
        val adapter = TreeInfoItemAdapter()
        adapter.setData(list, "horizontal")
        viewBinding?.rvlist?.adapter = adapter
    }

    private fun getData(j: Int): String? {
        return when (j) {
            0 -> tree!!.address
            1 -> tree!!.district
            2 -> tree!!.lat.toString() + ", " + tree!!.lng
            3 -> tree!!.name
            4 -> tree!!.condition
            5 -> tree!!.code
            6 -> tree!!.refNo
            7 -> tree!!.ovtNo
            8 -> tree!!.updatedAt
            else -> "N/A"
        }
    }

    private fun onClickDirection() {
        tree?.let { tree ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + tree.lat + "," + tree.lng)
            )
            startActivity(intent)
        }
    }

    private fun onClickReport() {
        tree?.let { tree ->
            var meta =
                """Android Version: ${Build.VERSION.RELEASE}""".trimIndent()
            meta += """SDK Level: ${Build.VERSION.SDK_INT}""".trimIndent()
            meta += """Version: ${getAppVersionName(context)}""".trimIndent()
            meta += """Brand: ${Build.BRAND}""".trimIndent()
            meta += """Model: ${Build.MODEL}""".trimIndent()
            meta += """Tree: $tree""".trimIndent()
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            // TODO set email in build config
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("team@collaction.hk"))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.pref_report_title))
            intent.putExtra(Intent.EXTRA_TEXT, meta)
            startActivity(Intent.createChooser(intent, getString(R.string.pref_report_title)))
        }
    }

    companion object {
        fun newInstance(args: Bundle?): TreeFragment {
            val fragment = TreeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTreeBinding.inflate(inflater, container, false)
}