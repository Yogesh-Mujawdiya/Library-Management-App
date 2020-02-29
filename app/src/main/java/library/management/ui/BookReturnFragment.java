package library.management.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import library.management.R;

public class BookReturnFragment extends Fragment {

    private String Host, URL_BookIssue ;
    EditText editTextId, editTextAccessNo;
    Spinner spinnerUserType;
    Button buttonGo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_return, container, false);
        editTextId = root.findViewById(R.id.editTextID);
        editTextAccessNo = root.findViewById(R.id.editTextAccessNo);
        spinnerUserType = (Spinner)root.findViewById(R.id.spinnerUserType);
        buttonGo = root.findViewById(R.id.btnGo);
        Host = getString(R.string.localhost);
        URL_BookIssue = Host+"/BookReturn.php";

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id, accessNo, userType;
                id = editTextId.getText().toString();
                accessNo = editTextAccessNo.getText().toString();
                userType = (String) spinnerUserType.getSelectedItem();
                if(id.isEmpty())
                    editTextId.setError("Please Type "+editTextId.getHint().toString());
                else if(accessNo.isEmpty())
                    editTextAccessNo.setError("Please Type "+editTextAccessNo.getHint().toString());
                else
                    BookReturn(id, accessNo, userType);
            }
        });

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    editTextId.setText("Roll No");
                else if(position==1)
                    editTextId.setText("Scholar Id.");
                else if(position==2)
                    editTextId.setText("Staff Id.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    void BookReturn(final String Id, final String AccessNo, final String UserType){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BookIssue,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (success.equals("True")) {
                                Toast.makeText(getActivity(), message , Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Upload Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Upload -- Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access",AccessNo);
                if(UserType.equals("Student")){
                    params.put("roll",Id);
                    params.put("options","Stu2");
                }
                else if(UserType.equals("Scholar")){
                    params.put("scho",Id);
                    params.put("options","Scho2");
                }
                else if(UserType.equals("Staff")){
                    params.put("staff",Id);
                    params.put("options","Staff");
                }

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}