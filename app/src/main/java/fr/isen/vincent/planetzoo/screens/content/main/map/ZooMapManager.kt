package fr.isen.vincent.planetzoo.screens.content.main.animals

import android.content.Context
import android.widget.Toast
import com.github.chrisbanes.photoview.PhotoView
import fr.isen.vincent.planetzoo.R

class ZooMapManager(private val context: Context, private val photoView: PhotoView) {

    init {
        photoView.setImageResource(R.drawable.mapzoo2)

        photoView.setOnPhotoTapListener { _, x, y ->
            val imageX = x * photoView.drawable.intrinsicWidth
            val imageY = y * photoView.drawable.intrinsicHeight

            Toast.makeText(context, "Clic à ($imageX, $imageY)", Toast.LENGTH_SHORT).show()
        }
    }
}
