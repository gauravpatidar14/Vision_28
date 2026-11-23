package com.example.vision28.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vision28.R;
import com.example.vision28.model.Work;
import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
  public Context c;
  ArrayList<Work> list;

  public RecAdapter(Context c, ArrayList<Work> list) {
    this.c = c;
    this.list = list;
  }

  public static class RecViewHolder extends RecyclerView.ViewHolder {
    TextView tx_work, tx_time;

    public RecViewHolder(View v) {
      super(v);
      tx_work = v.findViewById(R.id.tx);
      tx_time = v.findViewById(R.id.tx_d);
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
  }

  @Override
  public int getItemCount() {
    return list.size();
  }
}
