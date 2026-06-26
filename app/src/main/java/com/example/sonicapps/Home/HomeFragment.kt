package com.example.sonicapps.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sonicapps.AuthActivity
import com.example.sonicapps.Home.pertemuan_10.ProductAdapter
import com.example.sonicapps.Home.pertemuan_10.ProductModel
import com.example.sonicapps.Home.pertemuan_10.TenthActivity
import com.example.sonicapps.Home.pertemuan_13.ThirteenthActivity
import com.example.sonicapps.Home.pertemuan_3.ThirdActivity
import com.example.sonicapps.Home.pertemuan_3.ThirdResultActivity
import com.example.sonicapps.Home.pertemuan_4.FourthActivity
import com.example.sonicapps.Home.pertemuan_5.FifthActivity
import com.example.sonicapps.Home.pertemuan_7.SeventhActivity
import com.example.sonicapps.Home.pertemuan_9.NinthActivity
import com.example.sonicapps.R
import com.example.sonicapps.data.api.CatFactApiClient
import com.example.sonicapps.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import kotlin.math.log


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val productList = listOf(
        ProductModel("Sepatu Running Nike", "Rp 850.000", "https://picsum.photos/seed/shoe1/400/300"),
        ProductModel("Kemeja Flannel", "Rp 320.000", "https://picsum.photos/seed/shirt1/400/300"),
        ProductModel("Tas Ransel Laptop", "Rp 450.000", "https://picsum.photos/seed/bag1/400/300"),
        ProductModel("Jam Tangan Casio", "Rp 1.200.000", "https://picsum.photos/seed/watch1/400/300"),
        ProductModel("Headphone Sony", "Rp 1.500.000", "https://picsum.photos/seed/audio1/400/300"),
        ProductModel("Kaos Polos Premium", "Rp 150.000", "https://picsum.photos/seed/tshirt1/400/300"),
        ProductModel("Celana Jogger", "Rp 280.000", "https://picsum.photos/seed/pants1/400/300"),
        ProductModel("Sneakers Adidas", "Rp 950.000", "https://picsum.photos/seed/shoe2/400/300"),
        ProductModel("Dompet Kulit", "Rp 220.000", "https://picsum.photos/seed/wallet1/400/300"),
        ProductModel("Topi Baseball", "Rp 120.000", "https://picsum.photos/seed/hat1/400/300"),

        ProductModel("Mouse Gaming RGB", "Rp 275.000", "https://picsum.photos/seed/mouse1/400/300"),
        ProductModel("Keyboard Mechanical", "Rp 780.000", "https://picsum.photos/seed/keyboard1/400/300"),
        ProductModel("Monitor LG 24 Inch", "Rp 2.100.000", "https://picsum.photos/seed/monitor1/400/300"),
        ProductModel("Power Bank 20000mAh", "Rp 350.000", "https://picsum.photos/seed/powerbank1/400/300"),
        ProductModel("Charger Fast Charging", "Rp 180.000", "https://picsum.photos/seed/charger1/400/300"),
        ProductModel("Kacamata Hitam", "Rp 260.000", "https://picsum.photos/seed/glasses1/400/300"),
        ProductModel("Sandal Gunung", "Rp 340.000", "https://picsum.photos/seed/sandal1/400/300"),
        ProductModel("Jaket Hoodie", "Rp 410.000", "https://picsum.photos/seed/hoodie1/400/300"),
        ProductModel("Speaker Bluetooth", "Rp 620.000", "https://picsum.photos/seed/speaker1/400/300"),
        ProductModel("Tripod Kamera", "Rp 290.000", "https://picsum.photos/seed/tripod1/400/300"),

        ProductModel("Action Camera", "Rp 1.750.000", "https://picsum.photos/seed/camera1/400/300"),
        ProductModel("Smartphone Samsung", "Rp 4.500.000", "https://picsum.photos/seed/phone1/400/300"),
        ProductModel("Tablet Xiaomi", "Rp 3.250.000", "https://picsum.photos/seed/tablet1/400/300"),
        ProductModel("Laptop ASUS", "Rp 8.900.000", "https://picsum.photos/seed/laptop1/400/300"),
        ProductModel("Printer Epson", "Rp 2.350.000", "https://picsum.photos/seed/printer1/400/300"),
        ProductModel("Flashdisk 64GB", "Rp 95.000", "https://picsum.photos/seed/flashdisk1/400/300"),
        ProductModel("Harddisk External", "Rp 850.000", "https://picsum.photos/seed/hdd1/400/300"),
        ProductModel("Kursi Gaming", "Rp 1.950.000", "https://picsum.photos/seed/chair1/400/300"),
        ProductModel("Meja Kerja Minimalis", "Rp 1.450.000", "https://picsum.photos/seed/desk1/400/300"),
        ProductModel("Lampu LED RGB", "Rp 175.000", "https://picsum.photos/seed/lamp1/400/300"),

        ProductModel("Kompor Portable", "Rp 230.000", "https://picsum.photos/seed/stove1/400/300"),
        ProductModel("Blender Philips", "Rp 540.000", "https://picsum.photos/seed/blender1/400/300"),
        ProductModel("Rice Cooker", "Rp 480.000", "https://picsum.photos/seed/rice1/400/300"),
        ProductModel("Vacuum Cleaner", "Rp 1.250.000", "https://picsum.photos/seed/vacuum1/400/300"),
        ProductModel("Dispenser Air", "Rp 780.000", "https://picsum.photos/seed/dispenser1/400/300"),
        ProductModel("Botol Minum Tumbler", "Rp 110.000", "https://picsum.photos/seed/tumbler1/400/300"),
        ProductModel("Payung Lipat", "Rp 95.000", "https://picsum.photos/seed/umbrella1/400/300"),
        ProductModel("Bantal Leher Travel", "Rp 85.000", "https://picsum.photos/seed/pillow1/400/300"),
        ProductModel("Koper Kabin", "Rp 680.000", "https://picsum.photos/seed/luggage1/400/300"),
        ProductModel("Sepeda Lipat", "Rp 3.800.000", "https://picsum.photos/seed/bike1/400/300"),

        ProductModel("Helm Full Face", "Rp 920.000", "https://picsum.photos/seed/helmet1/400/300"),
        ProductModel("Sarung Tangan Motor", "Rp 140.000", "https://picsum.photos/seed/glove1/400/300"),
        ProductModel("Rak Buku Minimalis", "Rp 520.000", "https://picsum.photos/seed/bookshelf1/400/300"),
        ProductModel("Novel Best Seller", "Rp 98.000", "https://picsum.photos/seed/book1/400/300"),
        ProductModel("Paket Alat Lukis", "Rp 350.000", "https://picsum.photos/seed/paint1/400/300"),
        ProductModel("Gitar Akustik", "Rp 1.600.000", "https://picsum.photos/seed/guitar1/400/300"),
        ProductModel("Piano Keyboard", "Rp 2.750.000", "https://picsum.photos/seed/piano1/400/300"),
        ProductModel("Drone Mini", "Rp 1.950.000", "https://picsum.photos/seed/drone1/400/300"),
        ProductModel("Smartwatch Xiaomi", "Rp 670.000", "https://picsum.photos/seed/smartwatch1/400/300"),
        ProductModel("Router WiFi", "Rp 430.000", "https://picsum.photos/seed/router1/400/300")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
        }

        val sharedPref = requireContext().getSharedPreferences("session_user", MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit {
                        clear()
                    }

                    dialog.dismiss()

                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnToThird.setOnClickListener {
            val intent = Intent(requireContext(), ThirdActivity::class.java)
            startActivity(intent)
        }

        binding.btnToFourth.setOnClickListener {
            val intent = Intent(requireContext(), FourthActivity::class.java)
            /*tambahkan bagian berikut*/
            intent.putExtra("nama", "Politeknik Caltex Riau")
            intent.putExtra("asal", "Rumbai")
            intent.putExtra("usia", 25)
            startActivity(intent)
        }

        binding.btnToFifth.setOnClickListener {
            val intent = Intent(requireContext(), FifthActivity::class.java)
            startActivity(intent)
        }

        binding.btnToSeventh.setOnClickListener {
            val intent = Intent(requireContext(), SeventhActivity::class.java)
            startActivity(intent)
        }

        binding.btnToNinth.setOnClickListener {
            val intent = Intent(requireContext(), NinthActivity::class.java)
            startActivity(intent)
        }

        binding.btnToTenth.setOnClickListener {
            val intent = Intent(requireContext(), TenthActivity::class.java)
            startActivity(intent)
        }
        binding.btnToThirteenth.setOnClickListener {
            val intent = Intent(requireContext(), ThirteenthActivity::class.java)
            startActivity(intent)
        }
        binding.btnRefresh.setOnClickListener {
            loadCatFact()
        }
        loadCatFact()



    }
    private fun loadCatFact() {
        lifecycleScope.launch {
            try {
                val response = CatFactApiClient.apiService.getCatFact()
                binding.tvCatFact.text = "\"${response.fact}\""
            } catch (e: Exception) {
                binding.tvCatFact.text = "Gagal mengambil fakta kucing."

                Log.e("Error", e.toString())
            }
        }
    }
}