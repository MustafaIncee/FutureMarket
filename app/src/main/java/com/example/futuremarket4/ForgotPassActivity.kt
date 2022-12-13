package com.example.futuremarket4

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.futuremarket4.databinding.ActivityForgotPassBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPassBinding
    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog
    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

            firebaseAuth = FirebaseAuth.getInstance()

        binding.SifirlaBtn.setOnClickListener {
            var parolasifirla = binding.emailEt.text.toString().trim()
            if (TextUtils.isEmpty(parolasifirla)){
                binding.emailEt.error = "Lütfen emailinizi giriniz"
            }else{
                firebaseAuth.sendPasswordResetEmail(parolasifirla)
                    .addOnCompleteListener(this){e->
                        if (e.isSuccessful){
                            binding.psifirlamesaj.text= "E-mail adresinize sıfırlama bağlantısı gönderildi, Lütfen kontrol ediniz"
                        } else {
                            binding.psifirlamesaj.text= "Sıfırlama işlemi başarısız."
                        }
                    }
            }
        }


    }
}