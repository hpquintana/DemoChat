package io.github.hpquintana.demochat.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.hpquintana.demochat.R
import android.content.Intent
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class SplashScreenActivity : AppCompatActivity() {

    val FB_AUTH = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = FB_AUTH.currentUser

        if (currentUser != null) {
            val intent = Intent(
                applicationContext,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        } else {
//            Toast.makeText(this, "Not logged in!", Toast.LENGTH_LONG).show()
            val intent = Intent(
                applicationContext,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}
