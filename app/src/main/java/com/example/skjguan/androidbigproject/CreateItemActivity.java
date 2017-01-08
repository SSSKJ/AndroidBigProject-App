package com.example.skjguan.androidbigproject;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class CreateItemActivity extends AppCompatActivity {
    private ImageButton back;
    private EditText title;
    private Button save;
    private View importanceLevelView;
    private EditText content;
    private ImageButton press;
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner remindingTimeSpinner;
    private String year = "";
    private String month = "";
    private String day = "";
    private String remindingTime = "";
    private MagicProgressCircle circle;
    private boolean isCircleRunning = false;
    private AnimatorSet set = new AnimatorSet();
    private float importanceNum = 0;
    private String importanceLevel = "gray";
    private String createTime = "";
    String Url = "http://10.0.2.2:3000/";
    String info = "";
    String fileName = "info.txt";
    boolean haspost = false;
    private Date deaddate;
    private String deadline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        findViews();
        content.requestFocus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateItemActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
                yearSpinner = (Spinner) layout.findViewById(R.id.yearSpinner);
                monthSpinner = (Spinner) layout.findViewById(R.id.monthSpinner);
                daySpinner = (Spinner) layout.findViewById(R.id.daySpinner);
                remindingTimeSpinner = (Spinner) layout.findViewById(R.id.remindingTimeSpinner);
                spinner();
                new AlertDialog.Builder(CreateItemActivity.this)
                        .setTitle("到期时间：")
                        .setView(layout)
                        .setPositiveButton("发布", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Date date = new Date();
                                createTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                Log.d("", "year: " + year + " month: " + month + " day: " + day + " remindingTime: " + remindingTime + " importanceLevel: " + importanceLevel + " title: " + title.getText() + " content: " + content.getText() + " createTime: " + createTime);
//                                Intent intent = new Intent(CreateItemActivity.this, LoginActivity.class);
//                                startActivity(intent);
                                Connect r = new Connect(Url + "save");
                                new Thread(r).start();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        });

        set.play(ObjectAnimator.ofFloat(circle, "percent", 0, 100));
        set.setDuration(50000);

        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
    }

    public void anim() {
        if (isCircleRunning) {
            importanceNum = circle.getPercent();
            set.end();
        } else {
            circle.setVisibility(View.VISIBLE);
            set.start();
            isCircleRunning = true;
            press.setImageResource(R.mipmap.press_after);
        }
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circle.setVisibility(View.INVISIBLE);
                isCircleRunning = false;
                if (importanceNum <= 0.2) {
                    importanceLevel = "gray";
                    importanceLevelView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                } else if (importanceNum <= 0.6) {
                    importanceLevel = "green";
                    importanceLevelView.setBackgroundColor(Color.parseColor("#7bd67b"));
                } else {
                    importanceLevel = "red";
                    importanceLevelView.setBackgroundColor(Color.parseColor("#f16c6d"));
                }
                press.setImageResource(R.mipmap.press_before);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void spinner() {
        final String[] years = getResources().getStringArray(R.array.year);
        final String[] months = getResources().getStringArray(R.array.month);
        final String[] days = getResources().getStringArray(R.array.day);
        final String[] remindingTimes = getResources().getStringArray(R.array.remindingTime);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(CreateItemActivity.this,
                R.array.year, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(CreateItemActivity.this,
                R.array.month, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(CreateItemActivity.this,
                R.array.day, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> remindingTimeAdapter = ArrayAdapter.createFromResource(CreateItemActivity.this,
                R.array.remindingTime, android.R.layout.simple_spinner_item);

        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        daySpinner.setAdapter(dayAdapter);
        remindingTimeSpinner.setAdapter(remindingTimeAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = years[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                year = "2017";
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = months[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                month = "1";
            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = days[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = "1";
            }
        });

        remindingTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remindingTime = remindingTimes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                remindingTime = "1";
            }
        });
    }

    private void findViews() {
        back = (ImageButton) findViewById(R.id.back);
        title = (EditText) findViewById(R.id.title) ;
        save = (Button) findViewById(R.id.save);
        importanceLevelView = (View) findViewById(R.id.importanceLevel);
        content = (EditText) findViewById(R.id.content);
        press = (ImageButton) findViewById(R.id.press);
        circle = (MagicProgressCircle) findViewById(R.id.circle);
    }

    private class Connect implements Runnable {

        private String u;

        public Connect(String url) {
            this.u = url;
        }

        @Override
        public void run() {

            HttpURLConnection httpURLConnection = null;

            URL url = null;
            Message message = Message.obtain();

            try {
                url = new URL(u);

                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(8000);
                httpURLConnection.setReadTimeout(8000);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
                writer.write(putJson());
                writer.flush();
                writer.close();
                int responsecode = httpURLConnection.getResponseCode();
                String cookieval = httpURLConnection.getHeaderField("set-cookie");
                String sessionid = cookieval.substring(0, cookieval.indexOf(";"));//获取session
                User.session = sessionid;
                if (responsecode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    String result = response.toString();
                    Log.d("result", "................" + result);
                    reader.close();
                    message.what = 1;
                    info = result;
                } else message.what = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            if (message.what == 0)
                Toast.makeText(CreateItemActivity.this, "服务器发生错误,连接失败", Toast.LENGTH_SHORT).show();
            else if (message.what == 1) {
                if (info.equals("OK")) {
                    myDB db = new myDB(CreateItemActivity.this);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        deaddate = simpleDateFormat.parse(year + "-" + month + "-" + day);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    deadline = new SimpleDateFormat("yyyy-MM-dd").format(deaddate).toString();
                    int remindingTimeInt = Integer.parseInt(remindingTime);
                    db.insert(title.getText().toString(), content.getText().toString(), createTime, deadline, remindingTimeInt, importanceLevel);
                    Intent intent = new Intent(CreateItemActivity.this, ListActivity.class);
                    CreateItemActivity.this.setResult(2);
                    startActivity(intent);
                    finish();
                    //登录成功页面跳转
                    Log.d("Info", "....................OK");
                } else {
                    try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
                        fileOutputStream.write(("").getBytes());
                    } catch (IOException ex) {
                        Toast.makeText(CreateItemActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(CreateItemActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    String putJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username:", User.username);
            jsonObject.put("title", title.getText().toString());
            jsonObject.put("content", content.getText().toString());
            jsonObject.put("year", year);
            jsonObject.put("month", month);
            jsonObject.put("day", day);
            jsonObject.put("importanceLevel", importanceLevel);
            jsonObject.put("createTime", createTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
