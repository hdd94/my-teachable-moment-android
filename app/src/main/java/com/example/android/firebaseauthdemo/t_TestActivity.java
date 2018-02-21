package com.example.android.firebaseauthdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class t_TestActivity extends AppCompatActivity {
    private List<t_Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private t_MoviesAdapter mAdapter;
#
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new t_MoviesAdapter(movieList);

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

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new t_RecyclerTouchListener(getApplicationContext(), recyclerView, new t_RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                t_Movie movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {
        t_Movie movie = new t_Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new t_Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new t_Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new t_Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new t_Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new t_Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new t_Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new t_Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new t_Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new t_Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new t_Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new t_Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new t_Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new t_Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new t_Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new t_Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }

}


