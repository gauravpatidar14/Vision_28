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

        
        int[] srcPixels = new int[width * height];
        result.getPixels(srcPixels, 0, width, 0, 0, width, height);

        
        Bitmap maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas maskCanvas = new Canvas(maskBitmap);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(320f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.WHITE);
        maskCanvas.drawText(name, xPos, yPos, textPaint);

        int[] maskPixels = new int[width * height];
        maskBitmap.getPixels(maskPixels, 0, width, 0, 0, width, height);

        
        for (int i = 0; i < srcPixels.length; i++) {
            int alphaMask = Color.alpha(maskPixels[i]);
            if (alphaMask > 128) { 
                int pixel = srcPixels[i];
                int a = Color.alpha(pixel);
                
                int r = 255 - Color.red(pixel);
                int g = 255 - Color.green(pixel);
                int b = 255 - Color.blue(pixel);

                srcPixels[i] = Color.argb(a, r, g, b);
            }
        }

        
        result.setPixels(srcPixels, 0, width, 0, 0, width, height);

        maskBitmap.recycle();
        return result;
    }
}