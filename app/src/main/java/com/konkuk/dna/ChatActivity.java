package com.konkuk.dna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.konkuk.dna.R;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView)findViewById(R.id.rv_chat);
        recyclerView.setHasFixedSize(false);

        //recyclerView를 linearlayout방식으로 사용하기
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }
}
