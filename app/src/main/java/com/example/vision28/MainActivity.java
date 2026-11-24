package com.example.vision28;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vision28.adapter.RecAdapter;
import com.example.vision28.model.Work;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes7.dex */
public class MainActivity extends Activity {
  static final String REGEX2 = "^[a-zA-Z\\s]$";
  public static ArrayList<Work> list = null;
  Button bt;
  Button btAdd;
  int d = 1;
  EditText et;
  public String ts;
  TextView tx;
  TextView tx_d;
  Work w;
  public String ws;
  RecyclerView rec;
  RecAdapter adp;
  static final String REGEX = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) (0[1-9]|[12]\\d|3[01]),\\d{4} (0[1-9]|1[0-2]):[0-5]\\d (am|pm)";
  static final Pattern PATTERN = Pattern.compile(REGEX);
  static final String regex = "(?m)^(?![A-Z][a-z]{2}\\s+\\d{1,2},\\s*\\d{4}\\s+\\d{1,2}:\\d{2}\\s*[ap]m$).+";
  static final Pattern PATTERN2 = Pattern.compile(regex);

  @Override // android.app.Activity
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    rec = findViewById(R.id.rec);
    rec.setLayoutManager(new LinearLayoutManager(this));

    if (list == null) {
      list = new ArrayList<>();
    }
    getAllWork();
    adp = new RecAdapter(getBaseContext(), list);
    rec.setAdapter(adp);

  }

  /*
   * public void addWork() { System.currentTimeMillis(); DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy hh:mm a"); String formattedTimestamp = LocalDateTime.now().format(formatter); this.ts = formattedTimestamp; this.ws = "No Work Yet"; if (this.et.getText() != null && this.et.getText().toString() != null && this.et.getText().toString().trim().length() > 0) { this.ws = this.et.getText().toString().trim(); } this.w = new Work(this.ws, this.ts); this.tx.append("\n" + this.w.getTime() + "\n" + this.w.getName() + "\n"); this.tx_d.append("#d" + this.d); if (list != null) { list.add(this.w); saveListToStorage(); } }
   */

  public void getAllWork() {
    String s1 = readListFromStorage();
    // String s0 = getSummery(s1);
    Matcher mat = PATTERN.matcher(s1);
    Matcher mat2 = PATTERN2.matcher(s1);

    while (mat.find() && mat2.find()) {
      String matchedText = mat.group();
      String matchedText2 = mat2.group();
       list.add(new Work(matchedText2,matchedText));
    }
  }

  public void saveListToStorage() {
    File file = new File(Environment.getExternalStorageDirectory() + "/Vision_Data/vision.txt");
    if (!file.exists()) {
      try {
        file.createNewFile();
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));
        br.append((CharSequence) (String.valueOf(list.get(0).getTime()) + "\n"
            + list.get(0).getName() + "\n"));
        br.flush();
        br.close();
        Toast.makeText(getBaseContext(), "File Saved", 0).show();
        return;
      } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(getBaseContext(), e.getLocalizedMessage(), 0).show();
        return;
      }
    }
    try {
      if (list.size() > 0) {
        int j = list.size() - 1;
        BufferedWriter br2 = new BufferedWriter(new FileWriter(file, true));
        br2.append((CharSequence) (String.valueOf(list.get(j).getTime()) + "\n"
            + list.get(j).getName() + "\n"));
        br2.flush();
        br2.close();
        Toast.makeText(getBaseContext(), "File Saved", 0).show();
      }
    } catch (IOException e2) {
      Toast.makeText(getBaseContext(), "err:" + e2.getLocalizedMessage(), 0).show();
    }
  }

  public String readListFromStorage() {
    File file = new File(Environment.getExternalStorageDirectory() + "/Vision_Data/vision.txt");
    String string = null;
    if (!file.exists()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      while (true) {
        String s = br.readLine();
        if (s != null) {
          sb.append(String.valueOf(s) + "\n");
        } else {
          br.close();
          Toast.makeText(getBaseContext(), sb.toString(), 0).show();
          string = sb.toString();
          return string;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      Toast.makeText(getBaseContext(), e.getLocalizedMessage(), 0).show();
      return string;
    }
  }
}
