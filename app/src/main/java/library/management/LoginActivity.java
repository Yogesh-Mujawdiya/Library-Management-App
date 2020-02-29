package library.management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import library.management.Class.User;
import library.management.Controller.StoreData;

public class LoginActivity extends AppCompatActivity
{
    /* fields */
    EditText editTextEmail;
    EditText editTextPassword;
    TextView Register;
    Spinner spinnerUserType;
    ProgressBar progressBar;
    StoreData controller;
    private String Host , login_url ;
    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = new StoreData(this);
        // reference ui
        content = findViewById(R.id.content);
        editTextEmail = (EditText) findViewById(R.id.editTextUserEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Register = (TextView) findViewById(R.id.Register);
        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        Button loginButton = (Button) findViewById(R.id.buttonLogIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Host = getString(R.string.localhost);
        login_url =  Host+"/validate.php";

        // set listeners
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginUser();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * attempts to log the user in
     */

    public void LoginUser() {

        final String userid = editTextEmail.getText().toString().toLowerCase().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String userType = (String) spinnerUserType.getSelectedItem();

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            String name = jsonObject.getString("username");
                            String email = jsonObject.getString("email");
                            String Admin = jsonObject.getString("admin");

                            if (success.equals("True")) {
                                Toast.makeText(LoginActivity.this, message , Toast.LENGTH_SHORT).show();
                                User user = new User(userid, name, email, userType);
                                controller.setCurrentUser(user, Admin);
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("password", password);
                params.put("login_option",userType);
                if(userType.equals("Student"))
                    params.put("roll", userid);
                else if(userType.equals("Scholar"))
                    params.put("scho", userid);
                else if(userType.equals("Staff"))
                    params.put("staff", userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
