package com.okey.konserrezervasyon;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConsertAdapter extends RecyclerView.Adapter<ConsertAdapter.ConsertHolder>{

    private ArrayList<Consert> consertList;
    private Context context;
    private OnItemClickListener listener;

    public ConsertAdapter(ArrayList<Consert> consertList, Context context) {
        this.consertList = consertList;
        this.context = context;
    }

    @NonNull
    @Override
    public ConsertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.consert_item,parent,false);

        return new ConsertHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsertHolder holder, int position) {

        Consert consert = consertList.get(position);
        holder.setData(consert);
    }

    @Override
    public int getItemCount() {
        return consertList.size();
    }

    class ConsertHolder extends RecyclerView.ViewHolder{
        TextView txtArtistName,txtConsertDate,txtConsertPlace;
        ImageView imgConsertPicture;

        public ConsertHolder(@NonNull View itemView) {
            super(itemView);

            txtArtistName = (TextView) itemView.findViewById(R.id.consert_item_imgArtistName);
            txtConsertDate = (TextView) itemView.findViewById(R.id.consert_item_imgConsertDate);
            txtConsertPlace = (TextView) itemView.findViewById(R.id.consert_item_imgConsertPlace);
            imgConsertPicture = (ImageView) itemView.findViewById(R.id.consert_item_imgConsertPicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(consertList.get(position));
                    }
                }
            });

        }

        public void setData(Consert consert){
            this.txtArtistName.setText(consert.getArtistName());
            this.txtConsertDate.setText(consert.getConsertDate());
            this.txtConsertPlace.setText(consert.getConsertPlace());
            this.imgConsertPicture.setImageBitmap(consert.getConsertPicture());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Consert consert);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
