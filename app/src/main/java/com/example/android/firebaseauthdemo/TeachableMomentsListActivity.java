package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeachableMomentsListActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TextView textViewUserEmail;
    private Button btnLogout;

    private EditText editTextNickname, editTextForename, editTextSurname;
    private Button btnSave;
    private ImageButton btnCreate;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachable_moments_list);

        firebaseAuth = FirebaseAuth.getInstance();

        //User isn't logged in
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnCreate = (ImageButton) findViewById(R.id.ImageButtonCreate);
        btnCreate.setOnClickListener(this);

//        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
//        editTextForename = (EditText) findViewById(R.id.editTextForename);
//        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
//        btnSave = (Button) findViewById(R.id.buttonSave);
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
//        textViewUserEmail.setText("Welcome " + user.getEmail());
//
//        btnLogout = (Button) findViewById(R.id.buttonLogout);

        toolbar = (Toolbar) findViewById(R.id.toolbar_teachable_moments_list);
        setSupportActionBar(toolbar);

//        btnLogout.setOnClickListener(this);
//        btnSave.setOnClickListener(this);
    }
//
//    private void saveUserInformation() {
//        String nickname = editTextNickname.getText().toString().trim();
//        String forename = editTextForename.getText().toString().trim();
//        String surname = editTextSurname.getText().toString().trim();
//
//        UserInformation userInformation = new UserInformation(nickname, forename, surname);
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        databaseReference.child(user.getUid()).setValue(userInformation);
//
//        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        if (view == btnLogout) {
//            firebaseAuth.signOut();
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//
//        if (view == btnSave) {
//            saveUserInformation();
//        }
//    }

    @Override
    public void onClick(View view) {
        if(view == btnCreate) {
            startActivity(new Intent(this, TeachableMomentActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_settings:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
