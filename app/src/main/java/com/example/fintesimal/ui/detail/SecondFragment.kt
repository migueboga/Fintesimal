package com.example.fintesimal.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.fintesimal.R
import com.example.fintesimal.data.db.entities.Address
import com.example.fintesimal.databinding.SecondFragmentBinding
import com.example.fintesimal.util.Coroutines
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_history.view.*
import kotlinx.android.synthetic.main.second_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SecondFragment : Fragment(), SecondListener, KodeinAware, OnMapReadyCallback {

    override val kodein by kodein()
    private val factory: SecondViewModelFactory by instance()
    private lateinit var viewModel: SecondViewModel
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindUI: SecondFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.second_fragment,
                container,
                false)
        viewModel = ViewModelProvider(this, factory).get(SecondViewModel::class.java)
        bindUI.viewModel = viewModel
        viewModel.listener = this



        return bindUI.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val idAddress = arguments?.getInt("idAddress")
        idAddress?.let {
            viewModel.onReceiveData(idAddress)
        }
        map_detail.onCreate(savedInstanceState)
        map_detail.onResume()
        map_detail.getMapAsync(this)


        toolbar.setNavigationIcon(R.drawable.ic_nav_back)
        toolbar.setNavigationOnClickListener {
            viewModel.onBackStack(it)
        }
    }

    override fun onSuccess(address: Address) {
        Coroutines.main {
            title_detail.text = address.visited.toString()
            address_detail.text = address.streetName
            zone_detail.text = address.suburb
            icon_item_history
            if (address.visited){
                title_detail.text = "Visitada"
                title_detail.setTextColor(requireContext().getColor(R.color.backgroundGreen))
                icon_item_history.icon_item_history.setColorFilter(
                        ContextCompat.getColor(requireContext(),
                                R.color.backgroundGreen),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                action_container_detail.visibility = View.INVISIBLE
                //action_container_detail.layoutParams.height = 0
                val params = card_detail.layoutParams
                params.height = 230
                card_detail.layoutParams = params

            }else{
                title_detail.text = "Pendiente"
                title_detail.setTextColor(requireContext().getColor(R.color.gray_text_color))
                icon_item_history.icon_item_history.setColorFilter(
                        ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_text_color),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                action_container_detail.visibility = View.VISIBLE
            }

        }
    }

    override fun onFailure() {

    }

    override fun onGoLocation(address: Address) {
        val location = LatLng(address.latitude, address.longitude)
        googleMap.addMarker(
                MarkerOptions()
                        .position(location)
                        .icon(bitmapDescriptorFromVector(requireContext(),
                                if (address.visited)
                                    R.drawable.ic_visited_marker
                                else
                                    R.drawable.ic_marker))
                        .title("Marker in..")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            googleMap.setMinZoomPreference(14.0f)
            viewModel.onMapReady()
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}