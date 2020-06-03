package com.ensim.mic.slink.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapter_link;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Link;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LinksActivity extends AppCompatActivity {

    ImageView ivBack, ivMenu;
    TextView tvTitle;
    EditText etSearch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);


        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        List<Link> linkList = new ArrayList<>();

        String str = "https://www.gstatic.com/webp/gallery/4.sm.jpg";
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        System.out.println("str : " + str);

        linkList.add(new Link(1, 1, "episode 1", "www.google.com", str, "tajine twivjyat", new Date()));
        linkList.add(new Link(1, 1, "episode 1", "www.google.com", str, "tajine twivjyat", new Date()));
        linkList.add(new Link(1, 1, "episode 1", "www.google.com", str, "tajine twivjyat", new Date()));
        linkList.add(new Link(1, 1, "episode 1", "www.google.com", str, "tajine twivjyat", new Date()));

        mAdapter = new DataAdapter_link(this, linkList);
        recyclerView.setAdapter(mAdapter);

    }


}