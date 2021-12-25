package com.anawajha.babble.logic.socket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class ImageOperations {
  companion object{
      fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
          val ops = ByteArrayOutputStream()
          bitmap.compress(Bitmap.CompressFormat.JPEG, 0, ops)
          return ops.toByteArray()
      }// getBitmapAsByteArray

      fun getImage(byteArray: ByteArray): Bitmap {
          return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
      }// getImage
  }// companion object
}// class