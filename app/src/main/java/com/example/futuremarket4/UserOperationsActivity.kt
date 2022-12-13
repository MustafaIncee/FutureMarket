package com.example.futuremarket4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.futuremarket4.databinding.ActivityUserOperationsBinding
import com.google.firebase.auth.FirebaseAuth


private lateinit var auth: FirebaseAuth

private lateinit var binding: ActivityUserOperationsBinding

class UserOperationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOperationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser


        binding.hesapsilBtn.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext, "Hesabınız Silindi", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
        binding.guncelleBtn.setOnClickListener {
            startActivity(Intent(this, UpdateUserActivity::class.java))
        }
    }
}