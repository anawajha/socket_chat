package com.anawajha.babble.shared

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar

class Helpers{
    companion object{
        fun showSnackBar(view: View, title:String){
            Snackbar.make(view, title, Snackbar.LENGTH_LONG).apply {
                setAnimationMode(ANIMATION_MODE_SLIDE)
                setBackgroundTint(Color.parseColor("#E30425"))
                setTextColor(Color.parseColor("#FFFFFF"))
                show()
            }// apply
        }// show snackBar
    }// companion object
}// Helpers class
