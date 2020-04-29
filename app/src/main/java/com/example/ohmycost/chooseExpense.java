package com.example.ohmycost;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class  chooseExpense extends AppCompatActivity {

    DBHelper myDB;

    private TextView typeselect;
    private Button select, ok, back , view;
    private EditText cost;
    private EditText type_data;
    private Spinner typeSpin;
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<chooseExpense> listType = new ArrayList<chooseExpense>();
    private ArrayList<chooseExpense> listExpen = new ArrayList<chooseExpense>();
    private String typechoose, typeadd;

    private DBHelper mHelper;

    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_expense);
        typeselect = findViewById(R.id.typeselect);
        select = findViewById(R.id.select);
        ok = findViewById(R.id.ok);
        cost = findViewById(R.id.cost);
        back = findViewById(R.id.backToMain);
        typeSpin = findViewById(R.id.typespin);
        view = findViewById(R.id.view);

        myDB = new DBHelper(this);

        CreateTypeSelection();

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        typeSpin.setAdapter(adapterType);

        Bundle bundle = getIntent().getExtras();
        //String typeadd = bundle.getString("Type");
        typechoose = typeadd;

        typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeposition = String.valueOf(position);
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Other")){
                    Intent page_other = new Intent(chooseExpense.this, OtherType.class);
                    startActivity(page_other);
                        }

                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typechoose = (String) typeSpin.getSelectedItem();
                typeselect.setText(typechoose);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMain;
                backToMain = new Intent(chooseExpense.this, MainActivity.class);
                startActivity(backToMain);
            }
                });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry1 = typeselect.getText().toString();
                String newEntry2 = cost.getText().toString();
                //all.putExtra("type",newEntry1);
                //all.putExtra("expense",newEntry2);

                if (newEntry1!= "Type selection:" && newEntry2.length() !=0){
                    AddData_ex(newEntry1,newEntry2);
                }else{
                    Toast.makeText(chooseExpense.this, "YOU MUST PUT STH",Toast.LENGTH_LONG).show();
                }

                //startActivity(all);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseExpense.this,ViewListContents.class);

                startActivity(intent);
            }
        });

    }

    private void CreateTypeSelection() {

        type.add("Food");
        type.add("Bus");
        type.add("Other");
    }

    public void AddData_ex (String newEntry1,String newEntry2){
        boolean insetData = myDB.addData_ex(newEntry1,newEntry2);
        Toast.makeText(chooseExpense.this,newEntry1+newEntry2,Toast.LENGTH_LONG).show();
        if(insetData){
            Toast.makeText(chooseExpense.this,"Successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(chooseExpense.this,"Sth wrong",Toast.LENGTH_LONG).show();
        }
    }
}
