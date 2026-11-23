package com.example.vision28.service;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class PixelNameWriter {

  public static Bitmap writeNameUsingPixelData(Bitmap src, String name, int xPos, int yPos) {

    Bitmap result = src.copy(Bitmap.Config.ARGB_8888, true);
    int width = result.getWidth();
    int height = result.getHeight();

    int[] srcPixellayers = new int[width * height];
    result.getPixels(srcPixellayers, 0, width, 0, 0, width, height);

    Bitmap maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
    Canvas maskCanvas = new Canvas(maskBitmap);
    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setTextSize(300f);
    textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    textPaint.setColor(Color.GRAY);
    
   // maskCanvas.drawText(name, xPos, yPos, textPaint);
   
    int vindex = 18;
    int lineHeight=150;
    for (int j = 0; j < 3; j++) {
      if (name.length() > vindex * (j + 1))
        maskCanvas.drawText(name.substring(j * vindex, vindex * (j + 1)), xPos, yPos + lineHeight,
            textPaint);
      else
        maskCanvas.drawText(name.substring(j * vindex, name.length()), xPos, yPos + lineHeight, textPaint);
     
      lineHeight+=350;
    }
    int[] maskPixels = new int[width * height];
    maskBitmap.getPixels(maskPixels, 0, width, 0, 0, width, height);

    for (int i = 0; i < srcPixellayers.length; i++) {
      int alphaMask = Color.alpha(maskPixels[i]);
      if (alphaMask > 128) {
        int pixel = srcPixellayers[i];
        int a = Color.alpha(pixel);

        int r = 255 - Color.red(pixel);
        int g = 255 - Color.green(pixel);
        int b = 255 - Color.blue(pixel);

        srcPixellayers[i] = Color.argb(a, r, g, b);
      }
    }

    result.setPixels(srcPixellayers, 0, width, 0, 0, width, height);

    maskBitmap.recycle();
    return result;
  }
}