package com.okey.konserrezervasyon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BuyTicket extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ConsertAdapter adapter;
    NotificationManager notificationManager;
    String cPlace,cDate,cName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        mRecyclerView = (RecyclerView) findViewById(R.id.buyticket_activity_recyclerView);
        adapter = new ConsertAdapter(Consert.getData(this),this);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ConsertAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Consert consert) {

                cName = consert.getArtistName();
                cPlace = consert.getConsertPlace();
                cDate = consert.getConsertDate();

                String notiMassage = cName + " adlı sanatçı için " + cDate + " tarihli rezervasyonunuz yapılmıştır.";

                AlertDialog.Builder alert = new AlertDialog.Builder(BuyTicket.this);
                alert.setTitle("REZERVASYON")
                        .setMessage("Konser Rezervasyonu Yapmak İstiyor Musunuz ?")
                        .setIcon(R.drawable.baseline_question_mark_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendNotification(notiMassage);
                                Toast.makeText(getApplicationContext(),"H.O",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void sendNotification(String massage) {
        String channelID = "channel1";
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelID, "example channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelID)
                .setSmallIcon(R.drawable.konserium)
                .setContentTitle("REZERVASYON")
                .setContentText(massage);

                notificationManager.notify(1,builder.build());
    }
}