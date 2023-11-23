package com.cifpceuta.apprecyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<String> list_items;
    int orden = 0;
    int par = 0;
    public ItemAdapter(ArrayList<String> list_items){
        this.list_items=list_items;
    }
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elemento,parent,false);

        return new ItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.cardView.setCardBackgroundColor(Color.WHITE);
        if(position%2==0 && getPar()==1){
            holder.cardView.setCardBackgroundColor(Color.CYAN);
        } else if(position%2!=0 && getPar()==-1) {
            holder.cardView.setCardBackgroundColor(Color.CYAN);
        }
        holder.bindData(list_items.get(position));
    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        Button btnBorrar;
        CardView cardView;
        private ItemAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tv_nombre_elemento);
            btnBorrar = itemView.findViewById(R.id.btn_borrar_elemento);
            cardView = itemView.findViewById(R.id.cv_elemento);
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textoItem = list_items.get(getAdapterPosition());
                    Toast.makeText(itemView.getContext(),"Elemento eliminado "+textoItem,Toast.LENGTH_LONG).show();
                    list_items.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            if(getPar()==0){
                cardView.setCardBackgroundColor(Color.WHITE);
            }
        }
        void bindData(final String item){
            tvItem.setText(item);
        }
    }
    public void setFilterList(ArrayList<String> lista){
        list_items = lista;
        notifyDataSetChanged();
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getPar() {
        return par;
    }
}
