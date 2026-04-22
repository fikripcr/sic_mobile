package com.example.sonicapps


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sonicapps.databinding.ActivityAuthBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.edit

class AuthActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Kode ini harus selalu dipanggil saat butuh akses "user_pref"
        val sharedPref = getSharedPreferences("session_user", MODE_PRIVATE)

        //Kondisi jika isLogin bernilai true
//        val isLogin = sharedPref.getBoolean("isLogin", false)
//        if (isLogin) {
//            //Panggil Intent untuk ke MainActivity
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.btnLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if(username == password){
                sharedPref.edit {
                    putBoolean("isLogin", true)
                    putString("username", username)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Snackbar.make(binding.root, "Username atau Password salah!", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
    }
}