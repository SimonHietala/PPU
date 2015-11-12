package nsimhie.priceperusage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<Usage> usages = new ArrayList<Usage>();
    private RowAdapter adapter;

    public static final String PREFS_NAME = "PPU";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GetPrefs();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new RowAdapter(usages, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    @Override
    public void onPause()
    {
        super.onPause();  // Always call the superclass method first
        SetPrefs();
    }

    public void AddOnClick(View view)
    {

        EditText name = (EditText) findViewById(R.id.etName);
        EditText price = (EditText) findViewById(R.id.etPrice);

        if(!name.getText().toString().trim().equals("") && !price.getText().toString().trim().equals(""))
        {
            Usage usage = new Usage(name.getText().toString(), Double.parseDouble(price.getText().toString()), 0);
            name.setText("");
            price.setText("");

            usages.add(usage);
            adapter.notifyDataSetChanged();

            // Hide keyboard
            if (view != null)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    public void GetPrefs()
    {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        String json = preferences.getString("values",null);


        if(json != null)
        {
            try
            {
                JSONArray ja = new JSONArray(json);

                for (int i = 0; i < ja.length(); i++)
                {
                    JSONObject row = ja.getJSONObject(i);
                    Usage usage = new Usage(row.getString("name"), row.getDouble("price"), row.getInt("uses"));
                    usages.add(usage);
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetPrefs()
    {
        try
        {
            JSONArray ja = new JSONArray();

            for(int i = 0; i < usages.size(); i++)
            {
                ja.put(usages.get(i).toJson());
            }

            SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("values", ja.toString());
            editor.apply();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
