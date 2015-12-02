package second.test.lucassmc.academictask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CadastrarGrupoActivity extends AppCompatActivity {

    TextView nome, login;
    String login_user = "sidemar";
    private String nome_tarefa = "Trab06_EDO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_grupo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome = (TextView) findViewById(R.id.nome_membro);
        login = (TextView) findViewById(R.id.login_membro);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            login_user = (String) bd.get("login");
            nome_tarefa = (String) bd.get("nome_tarefa");
        }
    }

    public void enviar(View v) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.academictask.esy.es/cadastroGrupo.php?login_user="+login_user+"&nome="+nome.getText()+"&login="+login.getText()+"&id_tarefa="+nome_tarefa;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.v("RESPOSTA", response);
                        String [] nomes = response.split(";");

                        Intent itent = new Intent(getApplicationContext(), TaskGroupMembers.class);

                        for(int i = 0;i < nomes.length; i++) {
                            itent.putExtra(""+i, nomes[i]);
                        }

                        itent.putExtra("tamanho", ""+nomes.length);

                        startActivity(itent);
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
