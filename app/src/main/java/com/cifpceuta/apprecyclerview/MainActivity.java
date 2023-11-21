package com.cifpceuta.apprecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> elementos;
    private Toolbar toolbar;
    private RecyclerView rvElementos;
    private ItemAdapter adapter;
    private EditText etNewElement;
    private Button btnAddElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvElementos = findViewById(R.id.rv_elementos);
        toolbar = findViewById(R.id.toolbar);
        etNewElement = findViewById(R.id.et_new_elemento);
        btnAddElement = findViewById(R.id.btn_add_elemento);
        setSupportActionBar(toolbar);

        elementos = new ArrayList<>();
        char[] letras = new char[]{'a','b','c','d','e','f','g',
                'h','i','j','k','l','m','n','o','p','q','r',
                's','t','u','v','w','x','y','z'};
        for(int i=0; i<letras.length; i++){
            int random = (int)(letras.length*Math.random());
            elementos.add("Elemento "+letras[random]);
        }

        adapter = new ItemAdapter(elementos);
        rvElementos.setAdapter(adapter);
        rvElementos.setLayoutManager(new LinearLayoutManager(this));

        btnAddElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement(etNewElement.getText().toString());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.opcion1){
            ascendente();
        }
        else if(id == R.id.opcion2){
            descendente();
        }
        else if(id == R.id.opcion3){
            if(adapter.getOrden()==1){
                descendente();
            } else {
                ascendente();
            }
        }

        return true;
    }
    public void ascendente(){
        elementos.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        adapter.setOrden(1);
        adapter.notifyDataSetChanged();
    }
    public void descendente(){
        elementos.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        adapter.setOrden(-1);
        adapter.notifyDataSetChanged();
    }
    public void addElement(String element){
        elementos.add(element);
        adapter.notifyItemInserted(adapter.getItemCount());
    }
}