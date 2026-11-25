package com.example.vision28;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
  RelativeLayout rel;
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
    rel = findViewById(R.id.rel);
    rec.setLayoutManager(new LinearLayoutManager(this));

    if (list == null) {
      list = new ArrayList<>();
    }
    getAllWork();
    adp = new RecAdapter(getBaseContext(), list);
    rec.setAdapter(adp);

  }

  public void addWork(String wName) {
    System.currentTimeMillis();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy hh:mm a");
    String formattedTimestamp = LocalDateTime.now().format(formatter);
    this.ts = formattedTimestamp;
    this.ws = "No Work Yet";
    if (wName != null && wName.trim().length() > 0) {
      this.w = new Work(wName, this.ts);

      if (list != null) {
        list.add(this.w);
        adp.notifyItemInserted(list.size()-1);
        saveListToStorage();
      }
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);
    if (item.getItemId() == R.id.action_add) {
      Toast.makeText(this, "Menu Clicked", Toast.LENGTH_SHORT).show();
      openAddWorkDialog();
    }
    return true;
  }

  public void getAllWork() {
    if(list!=null)
      list.clear();
    String s1 = readListFromStorage();
    // String s0 = getSummery(s1);
    Matcher mat = PATTERN.matcher(s1);
    Matcher mat2 = PATTERN2.matcher(s1);

    while (mat.find() && mat2.find()) {
      String matchedText = mat.group();
      String matchedText2 = mat2.group();
      list.add(new Work(matchedText2, matchedText));
    }
  }

  public void openAddWorkDialog() {

    LayoutInflater inflater = getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.layout_input, null);

    EditText etInput = dialogView.findViewById(R.id.et);
    Button btnSubmit = dialogView.findViewById(R.id.bt_add);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(dialogView);

    AlertDialog dialog = builder.create();

    DisplayMetrics metrics = getResources().getDisplayMetrics();
    int halfHeight = metrics.heightPixels / 4;
    btnSubmit.setOnClickListener(v -> {
      String userText = etInput.getText().toString().trim();
      if (userText != null && userText.trim().length() > 0)
      {  Toast.makeText(getBaseContext(), userText, Toast.LENGTH_SHORT).show();
      addWork(userText);
      }  else
        Toast.makeText(getBaseContext(), "Enter Work Details", Toast.LENGTH_SHORT).show();

      dialog.dismiss();
    });

    dialog.show();
    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, halfHeight);
  }

  public void saveListToStorage() {
    File file = new File(Environment.getExternalStorageDirectory() + "/Vision_Data/vision.txt");
    if (!file.exists()) {
      try {
        file.createNewFile();
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));
        br.append((CharSequence) (String.valueOf(list.get(0).getTimeStamp() + "\n"
            + list.get(0).getName() + "\n")));
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
        br2.append((CharSequence) (String.valueOf(list.get(j).getTimeStamp()) + "\n"
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
