package chnginc.com.chng;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG2 = "login";
    Activity me = this;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.Json) TextView JsonView;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    String email;


    private static final String BASE_URL = "https://gentle-taiga-87228.herokuapp.com/user/login";
    OkHttpClient client = new OkHttpClient();

    class LoginTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject resultJson = new JSONObject(result);
                boolean newUser = (boolean) resultJson.get("newuser");
                if(newUser) {
                    setEmail();
                    Intent firstQuestions = new Intent(me,FirstQuestionsActivity.class);
                    startActivity(firstQuestions);

                } else {
                    setEmail();
                    Intent userPage = new Intent(me,UserPageActivity.class);
                    startActivity(userPage);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://gentle-taiga-87228.herokuapp.com/user/login");
            String responseString = "";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login",AppData.email));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonObject = new JSONObject(responseString);
                AppData.userName = (String) jsonObject.get("first_name");
                String usk = AppData.userName;
                AppData.userSurName = (String) jsonObject.get("last_name");
                String uskf = AppData.userSurName;

                AppData.userId = (int) jsonObject.get("id");
                return responseString;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseString;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppData.email = ((EditText)me.findViewById(R.id.input_email)).getText().toString();
                new LoginTask().execute();
                           }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });


    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);



//  email = _emailText.getText().toString();
        Intent email = new Intent(me, AppData.class);
        email.putExtra("login", _emailText.getText().toString());
        startActivity(email);
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    public void setEmail(){
        Intent email = new Intent(me, AppData.class);
        email.putExtra("login", _emailText.getText().toString());
        AppData.email=_emailText.getText().toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }
//
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
//
//    public class newTask extends AsyncTask<String, Void, String> {
//
//
//        //get JSON
//        @Override
//        protected String doInBackground(String... params) {
//            String Cookie;
//            try {
//                RequestBody formBody = new FormBody.Builder()
//                        .add("username", "здесь логин")
//                        .add("password", "здесь пароль")
//                        .build();
//                final Request request = new Request.Builder()
//                        .url(BASE_URL + "/login")
//                        .post(formBody)
//                        .build();
//                String result = null;
//                okhttp3.Response response = client.newCall(request).execute();
//
//                Cookie = response.priorResponse().header("Set-Cookie");
//
//                Request request1 = new Request.Builder()
//                        .url("http://young-depths-84274.herokuapp.com/account/")
//                        .addHeader("Cookie", Cookie)
//                        .addHeader("Accept", "application/json; q=0.5")
//                        .get()
//                        .build();
//                okhttp3.Response res = client.newCall(request1).execute();
//                result = res.body().string();
//
//                return result;
//
//            } catch (IOException e) {
//
//                return null;
//            }
//        }
//
//
//        //set Json(String) in Activity
//        @Override
//         protected void onPostExecute(String s){
//            super.onPostExecute(s);
//            JsonView.setText(s);
//        }
//    }
//


}
