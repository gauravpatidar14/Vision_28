package com.example.vision28;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.example.vision28.service.PixelNameWriter;
import java.io.File;

public class SecondActivity extends Activity {
  ImageView iv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    iv = findViewById(R.id.iv);
    Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.logo);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    // getActionBar().setDisplayUseLogoEnabled(true);
    getActionBar().setHomeAsUpIndicator(R.drawable.logo);
    File imgFile = new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/img.jpg");

    if (imgFile.exists()) {
      Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
     Bitmap result= PixelNameWriter.writeNameUsingPixelData(bitmap,"Gaurav Patidar\nImage Processing",200,2000);
               if(result!=null)
                iv.setImageBitmap(result);
    }
    
    
  }
}