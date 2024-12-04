package com.lokalook.lokalook.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.lokalook.lokalook.R
//import com.lokalook.lokalook.databinding.ActivityDetailBinding
//import com.lokalook.lokalook.ui.adapters.ImageCarouselAdapter
//import com.lokalook.lokalook.ui.viewmodels.MainViewModel
//import com.lokalook.lokalook.ui.viewmodels.ViewModelFactory
//import java.text.SimpleDateFormat
//import java.util.Locale

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var binding: ActivityDetailBinding
//    private lateinit var googleMap: GoogleMap
//
//    private val customTabs by lazy { CustomTabsIntent.Builder().build() }
//    private val viewModel by viewModels<MainViewModel> {
//        ViewModelFactory.getInstance(application)
//    }
//
//    override fun onMapReady(p0: GoogleMap) {
//        TODO("Not yet implemented")
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        configureToolbar()
//
//        val event = intent.getParcelableExtra<EventEntity>("EXTRA_EVENT")
//        event?.let { displayEventDetails(it) }
//    }
//
//    private fun configureToolbar() {
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
//        }
//    }
//
//    private fun displayEventDetails(event: EventEntity) {
//        binding.apply {
//            updateFavoriteIcon(event.isFavorite)
//
//            titleTextView.text = event.name
//            descriptionTextView.apply {
//                text = Html.fromHtml(event.description, Html.FROM_HTML_MODE_COMPACT)
//                movementMethod = LinkMovementMethod.getInstance()
//            }

    //            // Alamat dan tombol rute
//            addressTextView.text = "Alamat: ${event.address ?: "Alamat Tidak Tersedia"}"
//            routeButton.setOnClickListener { openRoute(event.latitude, event.longitude) }
//
//            // Ganti ImageView dengan ViewPager2 Carousel
//            setupCarousel(event.mediaList ?: emptyList())
//
//            favoriteFab.setOnClickListener { toggleEventFavorite(event) }
//            webpageFab.setOnClickListener { openEventWebPage(event.link) }
//
//            // Set up Google Maps
//            val mapFragment =
//                supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
//            mapFragment.getMapAsync(this@DetailActivity)
    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}
//    }

//    private fun setupCarousel(mediaList: List<String>) {
//        val adapter = ImageCarouselAdapter(mediaList)
//        binding.imageCarousel.apply {
//            this.adapter = adapter
//            orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        }
//    }
//
//    private fun formatEventDate(dateString: String?): String? {
//        return dateString?.let {
//            val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//            val outputFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
//            inputFormatter.parse(it)?.let { date -> outputFormatter.format(date) }
//        }
//    }
//
//    private fun updateFavoriteIcon(isFavorite: Boolean?) {
//        binding.favoriteFab.setImageResource(
//            if (isFavorite == true) R.drawable.ic_favorite else R.drawable.ic_favorite_border
//        )
//    }
//
//    private fun toggleEventFavorite(event: EventEntity) {
//        event.isFavorite = !(event.isFavorite ?: false)
//        updateFavoriteIcon(event.isFavorite)
//
//        if (event.isFavorite == true) {
//            viewModel.saveEvents(event)
//        } else {
//            viewModel.deleteEvents(event)
//        }
//    }
//
//    private fun openEventWebPage(url: String?) {
//        url?.let {
//            customTabs.launchUrl(this, Uri.parse(it))
//        }
//    }
//
//    private fun openRoute(latitude: Double?, longitude: Double?) {
//        latitude?.let {
//            longitude?.let { lon ->
//                val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
//                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                mapIntent.setPackage("com.google.android.apps.maps")
//                startActivity(mapIntent)
//            }
//        }
//    }

//    override fun onMapReady(map: GoogleMap?) {
//        googleMap = map ?: return
//        val event = intent.getParcelableExtra<EventEntity>("EXTRA_EVENT")
//        event?.let {
//            val location = LatLng(it.latitude ?: 0.0, it.longitude ?: 0.0)
//            googleMap.addMarker(MarkerOptions().position(location).title(it.name))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
//        }
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onMapReady(p0: GoogleMap) {
//        TODO("Not yet implemented")
//    }
//}