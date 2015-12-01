package second.test.lucassmc.academictask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Description extends AppCompatActivity {

    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        description = (TextView) findViewById(R.id.description2);

        Intent intent = getIntent();
        String descriptionS = intent.getExtras().getString("description");
        description.setText(descriptionS);
    }
}
