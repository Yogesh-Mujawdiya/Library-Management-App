package library.management.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class NewFragment extends Fragment {

    private String Host, URL_AddBook, URL_AddThesis, URL_AddCD, URL_AddJournal;
    LinearLayout Book, Thesis, CD, Journal;
    EditText editTextBookTitle, editTextBookAuthor, editTextBookAccessNo, editTextBookCallNo;
    EditText editTextBookPublication, editTextBookCost, editTextBookEdition;
    EditText editTextThesisTitle, editTextThesisScholarName, editTextThesisScholarId, editTextThesisMentorName;
    EditText editTextCDTitle, editTextCDName, editTextCDAccessNo;
    EditText editTextJournalTitle, editTextJournalName, editTextJournalAccessNo;
    Spinner spinnerAddDataType;
    Button buttonGo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);
        editTextBookTitle = root.findViewById(R.id.editTextBookTitle);
        editTextBookAuthor = root.findViewById(R.id.editTextBookAuthor);
        editTextBookAccessNo = root.findViewById(R.id.editTextBookAccessNo);
        editTextBookCallNo = root.findViewById(R.id.editTextBookCallNo);
        editTextBookPublication = root.findViewById(R.id.editTextBookPublication);
        editTextBookCost = root.findViewById(R.id.editTextBookCost);
        editTextBookEdition = root.findViewById(R.id.editTextBookEdition);
        editTextThesisTitle = root.findViewById(R.id.editTextThesisTitle);
        editTextThesisScholarName = root.findViewById(R.id.editTextThesisScholarName);
        editTextThesisScholarId = root.findViewById(R.id.editTextThesisScholarId);
        editTextThesisMentorName = root.findViewById(R.id.editTextThesisMentorName);
        editTextCDTitle = root.findViewById(R.id.editTextCDTitle);
        editTextCDName = root.findViewById(R.id.editTextCDName);
        editTextCDAccessNo = root.findViewById(R.id.editTextCDAccessNo);
        editTextJournalTitle = root.findViewById(R.id.editTextJournalTitle);
        editTextJournalName = root.findViewById(R.id.editTextJournalName);
        editTextJournalAccessNo = root.findViewById(R.id.editTextJournalAccessNo);
        Book = root.findViewById(R.id.Book);
        Thesis = root.findViewById(R.id.Thesis);
        CD = root.findViewById(R.id.CD);
        Journal = root.findViewById(R.id.Journal);
        spinnerAddDataType = (Spinner) root.findViewById(R.id.spinnerAddDataType);
        buttonGo = root.findViewById(R.id.btnGo);
        Host = getString(R.string.localhost);
        URL_AddBook = Host + "/AddBook.php";
        URL_AddThesis = Host + "/AddThesis.php";
        URL_AddCD = Host + "/AddCD.php";
        URL_AddJournal = Host + "/AddJournal.php";

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DataType = (String) spinnerAddDataType.getSelectedItem();
                if (DataType.equals("Add a Book")) {
                    String Title, Author, AccessNo, CallNo, Publication, Cost, Edition;
                    Title = editTextBookTitle.getText().toString();
                    Author = editTextBookAuthor.getText().toString();
                    AccessNo = editTextBookAccessNo.getText().toString();
                    CallNo = editTextBookCallNo.getText().toString();
                    Publication = editTextBookPublication.getText().toString();
                    Cost = editTextBookCost.getText().toString();
                    Edition = editTextBookEdition.getText().toString();
                    if (Title.isEmpty())
                        editTextBookTitle.setError("Please Type Book Title");
                    else if (Author.isEmpty())
                        editTextBookAuthor.setError("Please Type Book Author Name");
                    else if (AccessNo.isEmpty())
                        editTextBookAccessNo.setError("Please Type Book Access No.");
                    else if (CallNo.isEmpty())
                        editTextBookCallNo.setError("Please Type Book Call No.");
                    else if (Publication.isEmpty())
                        editTextBookPublication.setError("Please Type Book Publication");
                    else if (Cost.isEmpty())
                        editTextBookCost.setError("Please Type Book Cost");
                    else if (Edition.isEmpty())
                        editTextBookEdition.setError("Please Type Book Edition");
                    else
                        AddBook(Title, Author, AccessNo, CallNo, Publication, Cost, Edition);
                } else if (DataType.equals("Add a Thesis")) {
                    String Title, ScholarName, ScholarId, MentorName;
                    Title = editTextThesisTitle.getText().toString();
                    ScholarName = editTextThesisScholarName.getText().toString();
                    ScholarId = editTextThesisScholarId.getText().toString();
                    MentorName = editTextThesisMentorName.getText().toString();
                    if (Title.isEmpty())
                        editTextBookTitle.setError("Please Type Thesis Title");
                    else if (ScholarName.isEmpty())
                        editTextThesisScholarName.setError("Please Type Scholar Name");
                    else if (ScholarId.isEmpty())
                        editTextThesisScholarId.setError("Please Type Scholar Id");
                    else if (MentorName.isEmpty())
                        editTextThesisMentorName.setError("Please Type Mentor Name");
                    else
                        AddThesis(Title, ScholarName, ScholarId, MentorName);
                } else if (DataType.equals("Add a CD")) {
                    String Title, Name, AccessNo;
                    Title = editTextCDTitle.getText().toString();
                    Name = editTextCDName.getText().toString();
                    AccessNo = editTextCDAccessNo.getText().toString();
                    if (Title.isEmpty())
                        editTextCDTitle.setError("Please Type CD Title");
                    else if (Name.isEmpty())
                        editTextCDName.setError("Please Type CD Name");
                    else if (AccessNo.isEmpty())
                        editTextCDAccessNo.setError("Please Type Access No.");
                    else
                        AddCD(Title, Name, AccessNo);

                } else if (DataType.equals("Add a Journal")) {
                    String Title, Name, AccessNo;
                    Title = editTextJournalTitle.getText().toString();
                    Name = editTextJournalName.getText().toString();
                    AccessNo = editTextJournalAccessNo.getText().toString();
                    if (Title.isEmpty())
                        editTextJournalTitle.setError("Please Type Journal Title");
                    else if (Name.isEmpty())
                        editTextJournalName.setError("Please Type Journal Name");
                    else if (AccessNo.isEmpty())
                        editTextJournalAccessNo.setError("Please Type Access No.");
                    else
                        AddJournal(Title, Name, AccessNo);

                }

            }
        });


        spinnerAddDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Book.setVisibility(View.VISIBLE);
                    Thesis.setVisibility(View.GONE);
                    CD.setVisibility(View.GONE);
                    Journal.setVisibility(View.GONE);
                } else if (position == 1) {
                    Book.setVisibility(View.GONE);
                    Thesis.setVisibility(View.VISIBLE);
                    CD.setVisibility(View.GONE);
                    Journal.setVisibility(View.GONE);
                } else if (position == 2) {
                    Book.setVisibility(View.GONE);
                    Thesis.setVisibility(View.GONE);
                    CD.setVisibility(View.VISIBLE);
                    Journal.setVisibility(View.GONE);
                } else if (position == 3) {
                    Book.setVisibility(View.GONE);
                    Thesis.setVisibility(View.GONE);
                    CD.setVisibility(View.GONE);
                    Journal.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    private void AddBook(final String title, final String author, final String accessNo, final String callNo, final String publication, final String cost, final String edition) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AddBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
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
                params.put("title", title);
                params.put("author", author);
                params.put("access", accessNo);
                params.put("call", callNo);
                params.put("publication", publication);
                params.put("cost", cost);
                params.put("edition", edition);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void AddThesis(final String title, final String scholarName, final String scholarId, final String mentorName) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AddThesis,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
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
                params.put("title", title);
                params.put("scholarName", scholarName);
                params.put("scholarId", scholarId);
                params.put("mentorName", mentorName);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void AddCD(final String title, final String name, final String accessNo) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AddCD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
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
                params.put("title", title);
                params.put("name", name);
                params.put("access", accessNo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void AddJournal(final String title, final String name, final String accessNo) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AddJournal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
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
                params.put("title", title);
                params.put("name", name);
                params.put("access", accessNo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}