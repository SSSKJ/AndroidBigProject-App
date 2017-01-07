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
    private String importanceLevel = "grey";

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
                                Log.d("", "year: " + year + " month: " + month + " day: " + day + " remindingTime: " + remindingTime + " importanceLevel: " + importanceLevel + " title: " + title.getText() + " content: " + content.getText());
                                Intent intent = new Intent(CreateItemActivity.this, LoginActivity.class);
                                startActivity(intent);
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
                    importanceLevel = "grey";
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
}
