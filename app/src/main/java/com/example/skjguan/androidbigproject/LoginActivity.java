package com.example.skjguan.androidbigproject;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText UserName, PassWord, DoubleCheck;
    CheckBox Remember;
    TextView RegisterT, LoginT, Line1, Line2;
    Button Clear, LoginB;
    TableRow DoubleCheckRow;
    String Url = "localhost:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();

        RegisterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginB.getText().toString().equals("登录")) {
                    LoginT.setTextColor(RegisterT.getCurrentTextColor());
                    RegisterT.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.textback));
                    Line1.setVisibility(View.GONE);
                    Line2.setVisibility(View.VISIBLE);
                    Remember.setVisibility(View.GONE);
                    DoubleCheckRow.setVisibility(View.VISIBLE);
                    LoginB.setText("注册");
                    Clear();
                }
            }
        });

        LoginT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginB.getText().toString().equals("注册")) {
                    RegisterT.setTextColor(LoginT.getCurrentTextColor());
                    LoginT.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.textback));
                    Line1.setVisibility(View.VISIBLE);
                    Line2.setVisibility(View.GONE);
                    Remember.setVisibility(View.VISIBLE);
                    DoubleCheckRow.setVisibility(View.GONE);
                    LoginB.setText("登录");
                    Clear();
                }
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });

        LoginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    String s = LoginB.getText().toString();
                    switch (s) {
                        case "注册":
                            if (CheckFill()) {
                                if (DoubleCheck.getText().toString().equals(PassWord.getText().toString())) {
                                    Connect r = new Connect(Url + "register");
                                    new Thread(r).start();
                                } else
                                    Toast.makeText(LoginActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "登录":
                            if (CheckFill()) {
                                Connect l = new Connect(Url + "login");
                                new Thread(l).start();
                            }
                            break;

                        default:
                            break;
                    }
                } else
                    Toast.makeText(LoginActivity.this, "当前没有可用网络！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void bind() {
        UserName = (EditText)findViewById(R.id.username);
        PassWord = (EditText)findViewById(R.id.password);
        DoubleCheck = (EditText)findViewById(R.id.doublecheck);
        Remember = (CheckBox)findViewById(R.id.remember);
        RegisterT = (TextView)findViewById(R.id.MainRegister);
        LoginT = (TextView)findViewById(R.id.MainLogin);
        Line1 = (TextView)findViewById(R.id.MainLine1);
        Line2 = (TextView)findViewById(R.id.MainLine2);
        Clear = (Button)findViewById(R.id.clearButton);
        LoginB = (Button)findViewById(R.id.loginButton);
        DoubleCheckRow = (TableRow)findViewById(R.id.doublecheckRow);
    }

    boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    void Clear(){
        UserName.setText("");
        PassWord.setText("");
        DoubleCheck.setText("");
        Remember.setChecked(false);
    }


    private class Connect implements Runnable {

        private String url;

        public Connect(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Message message = Message.obtain();
            message.what = 0;
            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Toast.makeText(LoginActivity.this, "Connect" + message.what, Toast.LENGTH_SHORT).show();

        }
    };

    String putJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", UserName.getText().toString());
            jsonObject.put("password", PassWord.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    boolean CheckFill() {

        if (UserName.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (PassWord.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
