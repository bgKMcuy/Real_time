package id.skripsi.kaem.realtime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fieldSatu()
        fieldDua()
        fieldTiga()
    }

    private fun fieldSatu(){
        val buttonField1 = findViewById<Button>(R.id.tombol_1)
        buttonField1.setOnClickListener {
            startActivity(Intent(this,Field1::class.java))
            Toast.makeText(this,"menampilkan field 1", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fieldDua(){
        val buttonField2 = findViewById<Button>(R.id.tombol_2)
        buttonField2.setOnClickListener {
            startActivity(Intent(this,Field2::class.java))
            Toast.makeText(this,"menampilkan field 2", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fieldTiga(){
        val buttonField3 = findViewById<Button>(R.id.tombol_3)
        buttonField3.setOnClickListener {
            startActivity(Intent(this,Field3::class.java))
            Toast.makeText(this,"menampilkan field 3", Toast.LENGTH_SHORT).show()
        }
    }
}