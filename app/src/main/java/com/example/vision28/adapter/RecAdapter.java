package com.example.vision28.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vision28.R;
import com.example.vision28.model.Work;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
  public Context c;
  ArrayList<Work> list;

  public RecAdapter(Context c, ArrayList<Work> list) {
    this.c = c;
    this.list = list;
  }

  public static class RecViewHolder extends RecyclerView.ViewHolder {
    public TextView tx_work, tx_time, tx_date, tx_day;

    public RecViewHolder(View v) {
      super(v);
      tx_work = v.findViewById(R.id.tx);
      tx_time = v.findViewById(R.id.tx_d);
      tx_date = v.findViewById(R.id.tx_date);
      tx_day = v.findViewById(R.id.tx_day);
    }
  }

  @Override
  public RecAdapter.RecViewHolder onCreateViewHolder(ViewGroup p, int p1) {
    View v = LayoutInflater.from(c).inflate(R.layout.layout_work_entry, p, false);
    return new RecAdapter.RecViewHolder(v);
  }

  @Override
  public void onBindViewHolder(RecAdapter.RecViewHolder p, int p1) {
    Work w = list.get(p1);
    p.tx_work.setText(w.getName());
    p.tx_time.setText(w.getTime());
    p.tx_date.setText(w.getDate());
    p.tx_day.setText(getDayName(w.getDate()));

    p.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        int pos = p.getAdapterPosition();
        // Toast.makeText(c, "long click()" + pos, Toast.LENGTH_SHORT).show();

        removeEntryFromFile(list.get(pos));
        list.remove(pos);
        notifyItemRemoved(pos);
        return true;
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public String getDayName(String s) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d,yyyy", Locale.ENGLISH);
    LocalDate ld = LocalDate.parse(s, formatter);
    String dn = ld.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    Toast.makeText(c, dn, Toast.LENGTH_SHORT).show();
    return dn;
  }

  public void removeEntryFromFile(Work w) {
    String cLine;
    String nLine;
    File file = new File(Environment.getExternalStorageDirectory() + "/Vision_Data/vision.txt");
    File temp = new File(
        Environment.getExternalStorageDirectory() + "/Vision_Data/vision_temp.txt");
    if (file.exists()) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));
        if (!temp.exists())
          temp.createNewFile();
        while ((cLine = br.readLine()) != null) {

          if (cLine.equals(w.getTimeStamp())) {
            bw.write("");
            cLine = br.readLine();
            if (cLine != null)
              bw.write("");
            while ((nLine = br.readLine()) != null) {
              bw.write(nLine);
              bw.newLine();
            }
            bw.flush();
            bw.close();
            br.close();
            Toast.makeText(c, "Del:" + cLine, Toast.LENGTH_SHORT).show();
            break;
          } else {
            bw.write(cLine);
            bw.newLine();
            Toast.makeText(c, "else", Toast.LENGTH_SHORT).show();

          }

        }
        bw.close();
        br.close();
      } catch (IOException e) {
        Toast.makeText(c, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    }
    if (file.delete()) {
      Toast.makeText(c, "Original File Del Success", Toast.LENGTH_SHORT).show();
      if (temp.renameTo(file)) {
        Toast.makeText(c, "Rename Success", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(c, "Rename Failed", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(c, "Original File Del Failed", Toast.LENGTH_SHORT).show();
    }
  }
}