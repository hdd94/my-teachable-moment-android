package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeachableMomentsListActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ImageButton btnCreate;
    private ListView listViewTeacheableMoments;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachable_moments_list);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnCreate = (ImageButton) findViewById(R.id.imageButtonCreate);
        btnCreate.setOnClickListener(this);

        listViewTeacheableMoments = (ListView) findViewById(R.id.listViewTeachableMoments);

        String[] values = new String[] {
                //TODO #1 sollte immer rechts stehen
                "Thema 1                                                     #1",
                "Thema 2                                                     #2",
                "Thema 3                                                     #3",
                "Thema 4                                                     #4",
                "Thema 5                                                     #5",
                "Thema 6                                                     #6",
                "Thema 7                                                     #7",
                "Thema 8                                                     #8"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listViewTeacheableMoments.setAdapter(adapter);

        listViewTeacheableMoments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listViewTeacheableMoments.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_teachable_moments_list);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view) {
        if(view == btnCreate) {
            startActivity(new Intent(this, TeachableMomentActivity.class));
        }
    }

    // Appbar Icons initialisieren
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // OnClickListener von den Appbar Icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_settings:
                //TODO Vorübergehend als Test als Logout-Button initialisiert
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
