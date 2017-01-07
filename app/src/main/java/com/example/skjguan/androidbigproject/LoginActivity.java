package com.example.skjguan.androidbigproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {
    private Button login;

    EditText UserName, PassWord, DoubleCheck;
    CheckBox Remember;
    TextView RegisterT, LoginT, Line1, Line2;
    Button Clear, LoginB;
    TableRow DoubleCheckRow;
    String Url = "http://10.0.2.2:3000/";
    String info = "";
    String fileName = "info.txt";
    boolean haspost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();
        CheckRemeber();

        RegisterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haspost) {
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
            }
        });

        LoginT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haspost) {
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
                                    disable();
                                    Connect r = new Connect(Url + "register");
                                    new Thread(r).start();
                                } else
                                    Toast.makeText(LoginActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "登录":
                            if (CheckFill()) {
                                disable();
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

        private String u;

        public Connect(String url) {
            this.u = url;
        }

        @Override
        public void run() {

            HttpURLConnection httpURLConnection = null;

            URL url = null;

<<<<<<< HEAD
=======
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
                Log.d("code", "respond code" + responsecode);
                Log.d("session", sessionid);
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
>>>>>>> guan
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            enable();

            if (message.what == 0)
                Toast.makeText(LoginActivity.this, "服务器发生错误,连接失败", Toast.LENGTH_SHORT).show();
            else if (message.what == 1) {
                if (info.equals("OK")){
                    Remember();
                    //登录成功页面跳转
                    Log.d("Info", "....................OK");
                } else {
                    try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
                        fileOutputStream.write(("").getBytes());
                    } catch (IOException ex) {
                        Toast.makeText(LoginActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            }

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

        if (PassWord.getText().toString().length() < 6) {
            Toast.makeText(LoginActivity.this, "密码不能小于6位", Toast.LENGTH_SHORT).show();
        }

        String reg = "^[a-z0-9;]+$";
        if (!PassWord.getText().toString().matches(reg) || !UserName.getText().toString().matches(reg)) {
            Toast.makeText(LoginActivity.this, "用户名密码只能由数字和英文字母组成", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    boolean CheckRemeber() {
        try (FileInputStream fileInputStream = openFileInput(fileName)) {
            byte[] contents = new byte[fileInputStream.available()];
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (fileInputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            fileInputStream.close();
            arrayOutputStream.close();
            String result = arrayOutputStream.toString();
            Log.d("r", result.length() + "");
            if (result.equals(""))
                return false;
            String[] sl = result.split("\\|");
            UserName.setText(sl[0]);
            PassWord.setText(sl[1]);
            Remember.setChecked(true);
            /*disable();
            Connect l = new Connect(Url + "login");
            new Thread(l).start();*/
        } catch (IOException ex) {
        }
        return true;
    }

    void Remember() {
        if (Remember.isChecked()) {
            try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
                fileOutputStream.write((UserName.getText().toString() + "|" + PassWord.getText().toString() + "|").getBytes());
            } catch (IOException ex) {
                Toast.makeText(LoginActivity.this, "发生未知错误无法记住密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
                fileOutputStream.write(("").getBytes());
            } catch (IOException ex) {
                Toast.makeText(LoginActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void disable() {
        Remember.setClickable(false);
        UserName.setFocusable(false);
        UserName.setFocusableInTouchMode(false);
        PassWord.setFocusable(false);
        PassWord.setFocusableInTouchMode(false);
        DoubleCheck.setFocusable(false);
        DoubleCheck.setFocusableInTouchMode(false);
        LoginB.setClickable(false);
        haspost = true;
    }

    void enable() {
        Remember.setClickable(true);
        UserName.setFocusable(true);
        UserName.setFocusableInTouchMode(true);
        PassWord.setFocusable(true);
        PassWord.setFocusableInTouchMode(true);
        DoubleCheck.setFocusable(true);
        DoubleCheck.setFocusableInTouchMode(true);
        LoginB.setClickable(true);
        haspost = false;
    }

}
