package com.example.futuremarket4

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.futuremarket4.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog
    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var sifre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Giriş"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Lütfen bekleyiniz...")
        progressDialog.setMessage("Giriş yapılıyor...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // eğer tıklandıysa kayıt olma ekranına geç
        binding.Noaccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // giriş butonuna tıklandıysa
        binding.GirisBtn.setOnClickListener {
            validateData()
        }
        binding.forgotpass.setOnClickListener {
        //şifre yenileme ekranına gider
            startActivity(Intent(this, ForgotPassActivity::class.java))
            finish()
        }
    }

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        sifre = binding.sifreEt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Geçersiz email formatı"
        }
        else if (TextUtils.isEmpty(sifre)){
            binding.sifreEt.error = "Lütfen şifrenizi giriniz"
        }
        else{

            firebaseLogin()
        }


    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, sifre)
            .addOnSuccessListener {
                //giriş başarılı
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "$email hesabı ile giriş yapıldı", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //giriş başarısız
                progressDialog.dismiss()
                Toast.makeText(this, "Oturum açma işlemi başarısız oldu ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        //eğer kullanıcı zaten giriş yaptıysa ana ekrana git
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}