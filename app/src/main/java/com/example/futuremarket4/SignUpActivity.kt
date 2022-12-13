package com.example.futuremarket4

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.futuremarket4.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivitySignUpBinding


    private lateinit var actionBar: ActionBar


    private lateinit var progressDialog: ProgressDialog


    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var sifre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Üye Ol"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Lütfen bekleyiniz...")
        progressDialog.setMessage("Hesap oluşturuluyor...")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth = FirebaseAuth.getInstance()


        binding.SignBtn.setOnClickListener {

            validateData()
        }

    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        sifre = binding.sifreEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.emailEt.error = "Geçersiz email formatı"
        }
        else if (TextUtils.isEmpty(sifre)){

            binding.sifreEt.error = "Lütfen şifrenizi giriniz"
        }
        else if (sifre.length <5){
            binding.sifreEt.error = "Şifre minimum 6 karakterden oluşmalıdır"
    }
        else{
            firebaseSingUp()
        }

}

    private fun firebaseSingUp() {

        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, sifre)
            .addOnSuccessListener {
                //giriş başarılı
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email


                Toast.makeText(this, "$email hesabı ile hesap oluşturuldu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //giriş başarısız
                progressDialog.dismiss()
                Toast.makeText(this, "Kayıt oluşturulamadı. ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //önceki aktiviteye dönmek için
        return super.onSupportNavigateUp()
    }
}