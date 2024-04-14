package com.mobile.paozim

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class BackendActivity : AppCompatActivity() {
    private lateinit var text : TextView
    private lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backend)

        val email = intent.getStringExtra("email")
        val senha = intent.getStringExtra("senha")

        text = findViewById(R.id.tv_id)
        text.setText(senha)
        text = findViewById(R.id.tv_nome)
        text.setText(email)
        text = findViewById(R.id.tv_email)
        text.setText(email)
        text = findViewById(R.id.tv_telefone)
        text.setText(email)
        text = findViewById(R.id.tv_url)
        text.setText(email)

        image = findViewById(R.id.imageView)
        Picasso.get().load("https://panattos.com.br/uploads/produtos/2017/07/pao-frances-fermentacao-curta-massa-congelada.jpg").into(image)
    }
}

