package library.management;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

public class SignUpActivity extends AppCompatActivity
{
    /* UI */
    EditText editTextID;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextCourse;
    Spinner spinnerUserType;
    Spinner spinnerUserYear;
    ProgressBar progressBar;
    private String Host, register_url ;
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

          // reference ui
        content = findViewById(R.id.content);
        Host = this.getString(R.string.localhost) ;
        register_url = Host+"/adduser.php";
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCourse = (EditText) findViewById(R.id.editTextCourse);
        Button buttonSIgnUp = (Button) findViewById(R.id.buttonSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        spinnerUserYear = (Spinner) findViewById(R.id.spinnerUserYear);

        // add listeners
        buttonSIgnUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterUser();
            }
        });

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    editTextID.setText("Roll No");
                    editTextCourse.setVisibility(View.VISIBLE);
                    spinnerUserYear.setVisibility(View.VISIBLE);
                }
                else if(i==0){
                    editTextID.setText("Scholar Id.");
                    editTextCourse.setVisibility(View.GONE);
                    spinnerUserYear.setVisibility(View.GONE);
                }
                else if(i==0){
                    editTextID.setText("Staff Id.");
                    editTextCourse.setVisibility(View.GONE);
                    spinnerUserYear.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * signs up the user
     */

    private void RegisterUser() {
        final String id = editTextID.getText().toString();
        final String userName = editTextUserName.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String userType = (String) spinnerUserType.getSelectedItem();
        final String course = editTextCourse.getText().toString();
        final String year = (String) spinnerUserYear.getSelectedItem();

        // sign up in background
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(SignUpActivity.this, "Response Register Success ! \n" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Register Error ! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "Register -- Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("options", userType);
                params.put("user", userName);
                params.put("pass", password);
                params.put("email", email);
                if(userType.equals("Student")) {
                    params.put("roll",id) ;
                    params.put("course",course) ;
                    params.put("year",year) ;
                }
                else if(userType.equals("Scholar"))
                    params.put("scho",id) ;
                else if(userType.equals("Staff"))
                    params.put("staff",id) ;
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);

    }
}
