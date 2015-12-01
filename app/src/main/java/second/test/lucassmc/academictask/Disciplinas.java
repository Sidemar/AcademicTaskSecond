package second.test.lucassmc.academictask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import modelo.Turma;
import request.OAuthTokenRequest;

public class Disciplinas extends AppCompatActivity {

    //private String jsonResponse = "";
    private ProgressDialog pDialog;
    private TextView texto;
    private ArrayList<Turma> turmas = new ArrayList<>();
    private String login;
    private ArrayList<Item> lista = new ArrayList<Item>();
    private ListaAdapterItemDisciplina adapterItem;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);
        reqJsonTurma("graduacao", "2015");
        reqJsonLogin();

        listView = (ListView) findViewById(R.id.listView2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Log.v("ACAO", "ID: " + turmas.get(position).getId() + " Nome: " + turmas.get(position).getNome());
                Intent intent = new Intent(Disciplinas.this, Tasks.class);
                intent.putExtra("login", login);
                intent.putExtra("id", turmas.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void reqJsonLogin(){

        String urlJsonObj = "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info";
        OAuthTokenRequest.getInstance().resourceRequest(this, Request.Method.GET, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    login = jsonObject.getString("login");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SAIDA", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });
    }

    private void reqJsonTurma(String nivel, String periodo){
        int valor = 1;

        String urlJsonObj = "http://apitestes.info.ufrn.br/ensino-services/services/consulta/turmas/usuario/discente/"+nivel+"/"+periodo+"/"+valor;

        showpDialog();
        OAuthTokenRequest.getInstance().resourceRequest(this, Request.Method.GET, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        Turma turma = new Turma();
                        turma.setId(Long.parseLong(jsonobject.getString("id")));
                        turma.setNome(jsonobject.getString("nomeComponente"));
                        turmas.add(turma);
                    }

                    for (Turma turma : turmas) {
                        //jsonResponse += "Id: " + turma.getId() + "\n";
                        //jsonResponse += "Nome: " + turma.getNome() + "\n\n";
                        Item item = new Item(R.drawable.turma, turma.getNome(), "");
                        lista.add(item);
                    }

                    adapterItem = new ListaAdapterItemDisciplina(getApplicationContext(), lista);


                    listView.setAdapter(adapterItem);

                    //texto.setText(jsonResponse);
                    hidepDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SAIDA", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
