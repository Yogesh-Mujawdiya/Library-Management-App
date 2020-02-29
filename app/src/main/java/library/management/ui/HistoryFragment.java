package library.management.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.management.Class.Book;
import library.management.Class.User;
import library.management.Controller.HistoryAdapter;
import library.management.Controller.StoreData;
import library.management.R;

public class HistoryFragment extends Fragment {

    private String Host, URL_Book ;
    private List<Book> BookList ;
    private HistoryAdapter adapter ;
    User user;
    StoreData controller;
    RecyclerView recyclerView ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        Host = getString(R.string.localhost);
        URL_Book = Host+"/History.php";
        recyclerView = (RecyclerView) root.findViewById(R.id.HistoryRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BookList = new ArrayList<>();

        controller = new StoreData(getContext());
        user = controller.getCurrentUser();
        importNotification();
        setHasOptionsMenu(true);
        return root;
    }


    public void importNotification(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Book,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray jArray = json.getJSONArray("list");
                            for(int i=0; i<jArray.length(); i++){
                                JSONObject json_data = jArray.getJSONObject(i);
                                Book project = new Book(
                                        json_data.getString("title"),
                                        json_data.getString("access_no"),
                                        json_data.getString("issue_date"),
                                        json_data.getString("returned_on")
                                );
                                BookList.add(project);
                            }
                            adapter = new HistoryAdapter(getActivity(),BookList);
                            recyclerView.setAdapter(adapter);
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
                params.put("Id",user.getUserId());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}