package second.test.lucassmc.academictask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import modelo.Tarefa;
import request.OAuthTokenRequest;

public class Tasks extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ExpandableListAdapter expAdapter;
    private ArrayList<Group> expListItems;
    private ExpandableListView expandList;
    private ArrayList<Tarefa> tarefas = new ArrayList<>();
    private ArrayList<Group> list = new ArrayList<>();
    private ArrayList<Child> ch_list;
    private String login;
    private String idTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);



        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            login = (String) bd.get("login");
            idTurma = (String) bd.get("id");
            //Log.v("LOGIN", getName);
        }
        //reqJsonTarefas(Long.parseLong(id));
        reqJsonTarefas(Long.parseLong(idTurma));

        expandList = (ExpandableListView) findViewById(R.id.exp_list);

        expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

      /* You must make use of the View v, find the view by id and extract the text as below*/

                Log.v("ACAO", "ID: " + groupPosition);

                return true;  // i missed this
            }
        });

    }

    public ArrayList<Group> SetStandardGroups(ArrayList<Tarefa> ts) {

        int g_image = R.drawable.redalert;
        int g_image2 = R.drawable.bluealert;

        int Image = R.drawable.notes2;
        int image2 = R.drawable.group_users;

        int j = 0;

        for (Tarefa tarefa : ts) {
            Group gru = new Group();
            gru.setName(tarefa.getTitulo());
            gru.setDate(tarefa.getDataEntrega());
            if(j < 2)
                gru.setImage(g_image);
            else
                gru.setImage(g_image2);

            ch_list = new ArrayList<>();
            Child ch = new Child();
            ch.setName(tarefa.getConteudo());
            ch.setImage(Image);
            ch.setImage2(image2);
            ch_list.add(ch);

            gru.setItems(ch_list);
            list.add(gru);

            j++;
        }

        return list;
    }

    private void reqJsonTarefas(long idTurma){

        String urlJsonObj = "http://apitestes.info.ufrn.br/ensino-services/services/consulta/atividade/tarefa/usuario/" + idTurma;

        showpDialog();
        OAuthTokenRequest.getInstance().resourceRequest(this, Request.Method.GET, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.v("TAREFAS", response.toString());
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        Tarefa tarefa = new Tarefa();
                        tarefa.setTitulo(jsonobject.getString("titulo"));
                        tarefa.setConteudo(jsonobject.getString("conteudo"));
                        tarefa.setDataInicio(jsonobject.getString("dataInicio"));
                        tarefa.setDataEntrega(jsonobject.getString("dataEntrega"));
                        tarefa.setHorarioInicio(jsonobject.getString("horarioInicio"));
                        tarefa.setHorarioEntrega(jsonobject.getString("horarioEntrega"));
                        tarefas.add(0, tarefa);
                    }

                    expListItems = SetStandardGroups(tarefas);
                    expAdapter = new ExpandableListAdapter(Tasks.this, expListItems);
                    expandList.setAdapter(expAdapter);
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

    public void swDesctiption(View view){
        TextView description = (TextView) view.findViewById(R.id.description);

        Intent intent = new Intent(Tasks.this, Description.class);
        intent.putExtra("description", description.getText());
        startActivity(intent);
    }

    public void swNotes(View view){
        Intent intent = new Intent(Tasks.this, Notes.class);
        startActivity(intent);
    }

    public void swTaskGroup(View view){
        Intent intent = new Intent(Tasks.this, TaskGroupMembers.class);
        //intent.putExtra("nome_tarefa", "Trab06_EDO");
        startActivity(intent);
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
