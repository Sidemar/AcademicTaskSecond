package second.test.lucassmc.academictask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TaskGroupMembers extends AppCompatActivity {
    int tamanho;
    String[] nomes;
    TextView membros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_group_members);

        membros = (TextView) findViewById(R.id.membros);
        membros.setText("");

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            tamanho = Integer.parseInt((String) bd.get("tamanho"));
            nomes = new String[tamanho];
            for(int i = 0; i < tamanho; i++) {
                //nomes[i] = (String) bd.get(""+i);
                membros.setText(membros.getText() +((String) bd.get(""+i)) + "\n");
            }
        }

        enviar();
    }

    public void cadastrar(View v) {
        Intent i = new Intent(this, CadastrarGrupoActivity.class);
        startActivity(i);
    }

    public void enviar() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.academictask.esy.es/listarGrupo.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.v("RESPOSTA", response);
                        String [] nomes = response.split(";");
                        membros.setText("");
                        for(int i = 0; i < nomes.length; i++) {
                            //nomes[i] = (String) bd.get(""+i);
                            membros.setText(membros.getText() + nomes[i] + "\n");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
