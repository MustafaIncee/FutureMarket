package com.example.futuremarket4

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.futuremarket4.databinding.ActivityUpdateUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog
    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        var currentUser = firebaseAuth.currentUser
        binding.emailEt.setText(currentUser?.email)

        binding.bilgiguncelleBtn.setOnClickListener {
           var guncellemail = binding.emailEt.text.toString().trim()
            currentUser!!.updateEmail(guncellemail)
                .addOnCompleteListener { e->
                    if (e.isSuccessful){
                        Toast.makeText(applicationContext,"e-mail güncellendi",
                            Toast.LENGTH_LONG).show()
                    }else
                    {
                        Toast.makeText(applicationContext, "Email bilgileri güncellenemedi",
                            Toast.LENGTH_LONG).show()
                    }
                }
            var guncelleparola = binding.sifreEt.text.toString().trim()
            currentUser.updatePassword(guncelleparola)
                .addOnCompleteListener { e->
                    if (e.isSuccessful){
                        Toast.makeText(applicationContext,"Şifre güncellendi",
                        Toast.LENGTH_LONG).show()
                    }else
                    {
                        Toast.makeText(applicationContext, "Şifre güncellenemedi",
                        Toast.LENGTH_LONG).show()
                    }
                }
        }

    }


}

