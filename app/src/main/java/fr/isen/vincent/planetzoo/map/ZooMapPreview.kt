package fr.isen.vincent.planetzoo.screens.content.main.animals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.chrisbanes.photoview.PhotoView
import fr.isen.vincent.planetzoo.R

class ZooMapPreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoomap_preview)

        val photoView = findViewById<PhotoView>(R.id.photoView)
        ZooMapManager(this, photoView)
    }
}