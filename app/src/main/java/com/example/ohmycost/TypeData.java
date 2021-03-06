package com.example.ohmycost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TypeData extends AppCompatActivity {

    private static final String TAG = "TypeDataActivity";
    DatabaseHelper SQdb;

    private ListView TypelistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_data);
        Button back = findViewById(R.id.backToOthertype);

        TypelistView = (ListView) findViewById(R.id.TypelistView);
        SQdb = new DatabaseHelper(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToOthertype = new Intent(TypeData.this,OtherType.class);
                startActivity(backToOthertype);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Display data in the ListView. ");
        Intent bundle1 = getIntent();
        final String day = bundle1.getStringExtra("day");
        final String month_choose = bundle1.getStringExtra("month");
        final String year_choose = bundle1.getStringExtra("year");
        Cursor data = SQdb.getData();
        ArrayList<String> TypeList = new ArrayList<>();
        while(data.moveToNext()){
            TypeList.add(data.getString(1));
        }
        ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, TypeList);
        TypelistView.setAdapter(listAdapter);

        TypelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String name = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: You Click on:" + name);
                Cursor mydata = SQdb.getItemID(name);
                int itemID = -1;
                while (mydata.moveToNext()) {
                    itemID = mydata.getInt(0);

                }
                System.out.println(itemID);
                if (itemID > -1) {
                    Log.d(TAG, "OnItemClick: The ID is:" + itemID);
                    Intent editType = new Intent(TypeData.this, EditType.class);
                    editType.putExtra("id", itemID);
                    editType.putExtra("name", name);
                    editType.putExtra("day",day);
                    editType.putExtra("month",month_choose);
                    editType.putExtra("year",year_choose);
                    startActivity(editType);
                } else {
                    Toast.makeText(TypeData.this, "No ID with that name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
