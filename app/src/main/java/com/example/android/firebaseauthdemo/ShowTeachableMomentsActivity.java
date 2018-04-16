package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowTeachableMomentsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ImageButton buttonCreate;
    private ImageButton buttonRanking;
    private ListView listViewTeacheableMoments;

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private TeachableMomentInformationAdapter mAdapter;
    List<TeachableMomentInformation> tmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teachable_moments);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UnconfirmedMoments");

        buttonCreate = (ImageButton) findViewById(R.id.imageButtonCreate);
        buttonCreate.setOnClickListener(this);
        buttonRanking = (ImageButton) findViewById(R.id.imageButtonUserRanking);
        buttonRanking.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Teachable Moments");
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TeachableMomentInformation tm = tmList.get(position);
//                Toast.makeText(getApplicationContext(), tm.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                callTeachableMoment(tm);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view == buttonCreate) {
            startActivity(new Intent(this, CreateTeachableMomentActivity.class));
        }

        if (view == buttonRanking) {
            startActivity(new Intent(this, ShowUserRankingBestRatingActivity.class));
        }
    }

    // Appbar Icons initialisieren
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // OnClickListener von den Appbar Icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                    Toast.makeText(getApplicationContext(), "Sortiermenü anzeigen (Aktuell, " +
                            "Höchste Bewertung, Meistbewertet)", Toast.LENGTH_SHORT).show();
                    //TODO: SortBy anzeigen
                    //https://stackoverflow.com/questions/33260009/style-radio-button-and-text-inside-alertdialog
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, ShowMenuActivity.class));
                return true;

            default:
                // If we got here, the titel's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();


//        FirebaseRecyclerOptions<TeachableMomentInformation> options =
//                new FirebaseRecyclerOptions.Builder<TeachableMomentInformation>()
//                        .setQuery(databaseReference, TeachableMomentInformation.class)
//                        .build();
//
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<TeachableMomentInformation, TeachableMomentInformationAdapter.MyViewHolder>(options) {
//            @Override
//            public TeachableMomentInformationAdapter. onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.message, parent, false);
//
//                return new ChatHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(ChatHolder holder, int position, Chat model) {
//                // Bind the Chat object to the ChatHolder
//                // ...
//            }
//        };


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tmList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TeachableMomentInformation tm = postSnapshot.getValue(TeachableMomentInformation.class);
                    tmList.add(tm);
                }

                mAdapter = new TeachableMomentInformationAdapter(tmList);
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void callTeachableMoment(TeachableMomentInformation tm) {

        Intent intent = new Intent(this, ShowOneTeachableMomentActivity.class);
        intent.putExtra("TeachableMoment", tm);
        startActivity(intent);

    }
    //TODO: RecyclerViews sortieren


}
