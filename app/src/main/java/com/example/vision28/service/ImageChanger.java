package com.example.vision28.service;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageChanger {
  public Bitmap changeGreenColor(Bitmap originalBitmap,String newColor) {

    Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

    int width = mutableBitmap.getWidth();
    int height = mutableBitmap.getHeight();
    int[] pixels = new int[width * height];

    mutableBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

    int newR = Color.parseColor(newColor);
    int newG = Color.parseColor(newColor);
    int newB = Color.parseColor(newColor);

    for (int i = 0; i < pixels.length; i++) {
      int pixel = pixels[i];
      int alpha = Color.alpha(pixel);

      if (alpha == 0)
        continue;

      int r = Color.red(pixel);
      int g = Color.green(pixel);
      int b = Color.blue(pixel);

      if (g > 150 && g > r + 40 && g > b + 40)
        {
        pixels[i] = Color.argb(alpha,0,0 ,0);      
        }
    }
    mutableBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    return mutableBitmap;
  }
}