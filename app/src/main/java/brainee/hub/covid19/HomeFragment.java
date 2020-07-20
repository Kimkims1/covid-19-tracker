package brainee.hub.covid19;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class HomeFragment extends Fragment {

    public static final String STATS_URL = "https://api.covid19api.com/summary";


    private Context context;
    private ProgressBar progress_bar;
    private TextView totalCasesTv, newCasesTv, totaldeathsTv, newDeathsTv, totalRecoveredTv, newRecoveredTv;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progress_bar = view.findViewById(R.id.progress_bar);
        totalCasesTv = view.findViewById(R.id.totalCasesTv);
        newCasesTv = view.findViewById(R.id.newCasesTv);
        totaldeathsTv = view.findViewById(R.id.totaldeathsTv);
        newDeathsTv = view.findViewById(R.id.newDeathsTv);
        totalRecoveredTv = view.findViewById(R.id.totalRecoveredTv);
        newRecoveredTv = view.findViewById(R.id.newRecoveredTv);

        progress_bar.setVisibility(View.INVISIBLE);

        loadHomeData();

    }

    private void loadHomeData() {
        progress_bar.setVisibility(View.VISIBLE);

        /*JSON String request*/
        StringRequest request = new StringRequest(Request.Method.GET, STATS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleJsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progress_bar.setVisibility(View.GONE);
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void handleJsonResponse(String response) {
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONObject globalObject = jsonObject.getJSONObject("Global");
            String newConfirmed = globalObject.getString("NewConfirmed");
            String totalConfirmed = globalObject.getString("TotalConfirmed");
            String newDeaths = globalObject.getString("NewDeaths");
            String totalDeaths = globalObject.getString("TotalDeaths");
            String newRecovered = globalObject.getString("NewRecovered");
            String totalRecovered = globalObject.getString("TotalRecovered");

            newCasesTv.setText(newConfirmed);
            totalCasesTv.setText(totalConfirmed);
            newDeathsTv.setText(newDeaths);
            totaldeathsTv.setText(totalDeaths);
            newRecoveredTv.setText(newRecovered);
            totalRecoveredTv.setText(totalRecovered);

            progress_bar.setVisibility(View.GONE);

        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}