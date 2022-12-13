package com.example.futuremarket4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.futuremarket4.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {



private lateinit var binding: ActivityProfileBinding

private lateinit var actionBar: ActionBar

private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Profil"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.CikisBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }


    private fun checkUser() {

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email

            binding.emailTv.text = email
        }
        else{
            //kullanıcı olmadığı için giriş ekranına geri dön
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.hisseBtn.setOnClickListener {
            startActivity(Intent(this, StockMarketActivity::class.java))
            finish()
        }
        binding.CryptoBtn.setOnClickListener {
            startActivity(Intent(this, CryptoCurrencyActivity::class.java))

        }

        binding.islemBtn.setOnClickListener {
            startActivity(Intent(this, UserOperationsActivity::class.java))
            finish()
        }
    }

}