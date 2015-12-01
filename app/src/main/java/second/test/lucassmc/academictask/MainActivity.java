package second.test.lucassmc.academictask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import request.OAuthTokenRequest;

public class MainActivity extends AppCompatActivity {

    TextView title;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.AcademicTaskTitle);
        loginBtn = (Button) findViewById(R.id.btn_login);

        title.setText(Html.fromHtml("Academic<font color='#3949AB'>Task</font>"));

    }

    public void logar(View v) {
        Intent i = new Intent(this, Disciplinas.class);
        OAuthTokenRequest.getInstance().
                getTokenCredential(this,
                        "http://apitestes.info.ufrn.br/authz-server", "academic-task-id",
                        "academictask", i);
    }
}
