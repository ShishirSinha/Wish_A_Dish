package com.example.wishadish.ui.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wishadish.LoginSessionManager;
import com.example.wishadish.MainActivity;
import com.example.wishadish.Utility.MySingleton;
import com.example.wishadish.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.wishadish.ui.Reports.ReportsFragment.BASE_URL;
import static com.example.wishadish.ui.Reports.ReportsFragment.NO_OF_RETRY;
import static com.example.wishadish.ui.Reports.ReportsFragment.RETRY_SECONDS;

public class LoginActivity extends AppCompatActivity {

    private EditText mobEt,passEt;
    private Button loginBtn;

    private final String TAG = this.getClass().getSimpleName();
    String mobile,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobEt = findViewById(R.id.et_login_mobile);
        passEt = findViewById(R.id.et_login_pin);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = mobEt.getText().toString().trim();
                password = passEt.getText().toString().trim();

                if(password.length() == 0 || mobile.length() != 10){
                    Toast.makeText(LoginActivity.this, "Please enter valid mobile number and password!", Toast.LENGTH_SHORT).show();
                    mobEt.setText("");
                    passEt.setText("");
                    return;
                }

                verifyCredentialsFromDb(mobile,password);
            }
        });
    }

    public void verifyCredentialsFromDb(final String mob, final String password) {

        Log.e(TAG, "called : verifyCredentialsFromDb");

        String LOGIN_URL = BASE_URL + "/login";
        //mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, response);

                //mProgressBar.setVisibility(View.GONE);

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code == 0) {
                        Toast.makeText(LoginActivity.this, "Something went wrong verifyCredentials() !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(code == 2) {
                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (code == 3) {
                        Toast.makeText(LoginActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (code != 1) {
                        Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONObject jsonResult = jsonResponse.getJSONObject("details");
                    String mToken = jsonResponse.getString("token");

                    String mId   = jsonResult.getString("id");
                    String mUsername = jsonResult.getString("username");
                    String mPassword   = jsonResult.getString("password");
                    String mName= jsonResult.getString("name");
                    String mMobile= jsonResult.getString("mobile");
                    String mCompanyName    = jsonResult.getString("company_name");
                    String mGSTno  = jsonResult.getString("gst_no");
                    String mAddress  = jsonResult.getString("address");
                    String mTime  = jsonResult.getString("time");
                    String mEmail  = jsonResult.getString("email");
                    String mType  = jsonResult.getString("type");

                    LoginSessionManager mSession = new LoginSessionManager(LoginActivity.this);
                    mSession.createLoginSession(mId, mUsername, mPassword, mName, mMobile, mCompanyName, mGSTno, mAddress, mTime, mEmail, mType, mToken);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "verifyCredentials : Exception caught  " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mProgressBar.setVisibility(View.GONE);
                Log.e("error", error.toString());
                Toast.makeText(LoginActivity.this,"Error : verifyCredentials() !", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("mobile", mob);
                params.put("password", password);

                Log.e("mobile", mob);
                Log.e("password",password);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS, NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

    }
}
