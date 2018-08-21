package com.example.android.firebaseauthdemo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTeachableMomentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle;
    private EditText editTextTeachableMoment;
    private EditText editTextDate;
    private Button btnSave;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private DatePickerDialog datePickerDialog;

    private String userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teachable_moment);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
        editTextDate.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Teachable Moment");
        setSupportActionBar(toolbar);
        //Zurück-Button oben links
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUserNickname();
    }

    @Override
    public void onClick(View view) {
        if(view == btnSave) {
            saveTeachableMoment();
        }
        if(view == editTextDate) {
            showDatePickerDialog();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void saveTeachableMoment () {

        //TODO: !!!!!!!!!!!!!!!!!!!!!!! Ein Objekt herausfinden mit UserID, sodass in Firebase nach Object gesucht wird mit der passenden ID
        String userID = firebaseAuth.getCurrentUser().getUid();
        String creationDate = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());

        String id = databaseReference.push().getKey();
        String title = editTextTitle.getText().toString();
        String teachableMoment = editTextTeachableMoment.getText().toString();
        String date = editTextDate.getText().toString();

        if(TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Bitte gebe einen Titel ein.", Toast.LENGTH_SHORT).show();
            //TODO: Maximale Titellänge definieren
            return;
        }

        if(TextUtils.isEmpty(teachableMoment)) {
            Toast.makeText(this, "Bitte gebe ein Teachable Moment ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        _TeachableMomentInformation teachableMomentInformation = new _TeachableMomentInformation(id, title, teachableMoment, date, creationDate, userID, userNickname);
        databaseReference.child("UnconfirmedMoments").child(id).setValue(teachableMomentInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                Toast.makeText(getApplicationContext(), "Teachable Moment erfolgreich gespeichert und zur Überprüfung übergeben.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUserNickname() {
        String userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Benutzer").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               userNickname = dataSnapshot.getValue(_UserInformation.class).getNickname();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        String inputDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        Date todayDate = Calendar.getInstance().getTime();
                        try {
                            if (format.parse(inputDate).before(todayDate)) editTextDate.setText(dayOfMonth + "."
                                    + (monthOfYear + 1) + "." + year);
                            else Toast.makeText(getApplicationContext(), "Dein Datum liegt in der Zukunft. Bitte gebe ein gültiges Datum ein.", Toast.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}