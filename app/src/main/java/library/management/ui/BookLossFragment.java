package library.management.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

public class BookLossFragment extends Fragment {

    private String Host, URL_BookLoss ;
    EditText editTextId, editTextAccessNo, editTextNewBookTitle, editTextNewBookAccessNO, editTextBookFine;
    Spinner spinnerUserType, spinnerPay;
    Button buttonGo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_loss, container, false);
        editTextId = root.findViewById(R.id.editTextID);
        editTextAccessNo = root.findViewById(R.id.editTextAccessNo);
        editTextNewBookTitle = root.findViewById(R.id.editTextBookTitle);
        editTextNewBookAccessNO = root.findViewById(R.id.editTextBookAccessNO);
        editTextBookFine = root.findViewById(R.id.editTextFineAmount);
        spinnerUserType = (Spinner)root.findViewById(R.id.spinnerUserType);
        spinnerPay = (Spinner)root.findViewById(R.id.spinnerFine);
        buttonGo = root.findViewById(R.id.btnGo);
        Host = getString(R.string.localhost);
        URL_BookLoss = Host+"/BookLoss.php";

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String id, accessNo, userType, newBookTitle, newBookAccessNo, BookFine, FineType;
                id = editTextId.getText().toString();
                accessNo = editTextAccessNo.getText().toString();
                userType = (String) spinnerUserType.getSelectedItem();
                newBookTitle = editTextNewBookTitle.getText().toString();
                newBookAccessNo = editTextNewBookAccessNO.getText().toString();
                BookFine = editTextBookFine.getText().toString();
                FineType = (String) spinnerPay.getSelectedItem();
                if(id.isEmpty())
                    editTextId.setError("Please Type "+editTextId.getHint().toString());
                else if(accessNo.isEmpty())
                    editTextAccessNo.setError("Please Type Access No.");
                else {
                    if(FineType.equals("Pay Fine")) {
                        if(BookFine.isEmpty())
                            editTextBookFine.setError("Please Type Book Fine");
                        else
                            BookLoss(id, accessNo, userType, newBookTitle, newBookAccessNo, BookFine, FineType);
                    }
                    else {
                        if(newBookTitle.isEmpty())
                            editTextNewBookTitle.setError("Please Type New Book Title");
                        else if(newBookAccessNo.isEmpty())
                            editTextNewBookAccessNO.setError("Please Type New Book Access No.");
                        else
                            BookLoss(id, accessNo, userType, newBookTitle, newBookAccessNo, BookFine, FineType);
                    }
                }
            }
        });

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    editTextId.setHint("Roll No");
                else if(position==1)
                    editTextId.setHint("Scholar Id.");
                else if(position==2)
                    editTextId.setHint("Staff Id.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    editTextNewBookTitle.setVisibility(View.GONE);
                    editTextNewBookAccessNO.setVisibility(View.GONE);
                    editTextBookFine.setVisibility(View.VISIBLE);
                }
                else if(position==1) {
                    editTextNewBookTitle.setVisibility(View.VISIBLE);
                    editTextNewBookAccessNO.setVisibility(View.VISIBLE);
                    editTextBookFine.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    void BookLoss(final String Id, final String AccessNo, final String UserType, final String newBookTitle,
                  final String newBookAccessNo, final String BookFine, final String FineType){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BookLoss,
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
                if (FineType.equals("Pay Fine"))
                    params.put("replacement","bookrep");
                else
                    params.put("replacement","B");
                params.put("new_title",newBookTitle);
                params.put("new_access",newBookAccessNo);
                params.put("fine",BookFine);
                if(UserType.equals("Student")){
                    params.put("roll",Id);
                    params.put("options","Stu3");
                }
                else if(UserType.equals("Scholar")){
                    params.put("scho",Id);
                    params.put("options","Scho3");
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