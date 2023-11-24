package com.cifpceuta.apprecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> elementos;
    private Toolbar toolbar;
    private RecyclerView rvElementos;
    private ItemAdapter adapter;
    private SearchView svBusqueda;
    private FloatingActionButton fabAddElemento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvElementos = findViewById(R.id.rv_elementos);
        toolbar = findViewById(R.id.toolbar);
        svBusqueda = findViewById(R.id.sv_buscar_elemento);
        fabAddElemento = findViewById(R.id.fab_add_elemento);

        fabAddElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd(v);
            }
        });

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
    public void dialogAdd(View v){
        //Creamos el DIALOG asociado al ActivityMAIN
        Dialog dialog = new Dialog(MainActivity.this);

        //Le asociamos el layout correspondiente
        dialog.setContentView(R.layout.layout_dialog);

        //Recuperamos los views dentro de dicho layout para recuperar sus valores posteriormente
        EditText etTextoItem = dialog.findViewById(R.id.etTextoItem);
        Button btnDialogAdd = dialog.findViewById(R.id.btnDialogAdd);


        //Establecemos el listener para capturar datos y realizar acción de añadir
        btnDialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etTextoItem.getText().toString();
                // tb se puede realizar una validación de campo no vacio...
                elementos.add(item);
                //añadimos el item al final
                adapter.notifyItemInserted(elementos.size()-1);

                //hacemos desplazar la lista de items hasta dicho valor
                rvElementos.scrollToPosition(elementos.size()-1);

                //Esta llamada cierra el dialogo
                dialog.dismiss();
            }
        });


        //Esta llamada abre el diálogo
        dialog.show();
    }
}