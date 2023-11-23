package com.cifpceuta.apprecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> elementos;
    private Toolbar toolbar;
    private RecyclerView rvElementos;
    private ItemAdapter adapter;
    private EditText etNewElement;
    private Button btnAddElement;
    private SearchView svBusqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvElementos = findViewById(R.id.rv_elementos);
        toolbar = findViewById(R.id.toolbar);
        etNewElement = findViewById(R.id.et_new_elemento);
        btnAddElement = findViewById(R.id.btn_add_elemento);
        svBusqueda = findViewById(R.id.sv_buscar_elemento);
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

        svBusqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtroElementos(newText);
                return false;
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
        else if (id == R.id.opcion4){
            if(rvElementos.getLayoutManager().getClass().equals(LinearLayoutManager.class)){
                rvElementos.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                rvElementos.setLayoutManager(new LinearLayoutManager(this));
            }
        } else if (id == R.id.opcion5) {
            if(adapter.getPar()!=1) {
                adapter.setPar(1);
                adapter.notifyDataSetChanged();
            }
        } else if (id == R.id.opcion6){
            if(adapter.getPar()!=-1){
                adapter.setPar(-1);
                adapter.notifyDataSetChanged();
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
        adapter.setPar(0);
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
        adapter.setPar(0);
        adapter.notifyDataSetChanged();
    }
    public void addElement(String element){
        elementos.add(element);
        adapter.notifyItemInserted(adapter.getItemCount());
    }
    private void filtroElementos(String texto){
        ArrayList<String> resultadoElementos = new ArrayList<>();
        for(String s : elementos){
            if(s.toLowerCase().contains(texto.toLowerCase())){
                resultadoElementos.add(s);
            }
        }
        adapter.setFilterList(resultadoElementos);
    }

}