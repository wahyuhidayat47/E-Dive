package com.example.e_dive;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Tiket_Adapter extends RecyclerView.Adapter<Tiket_Adapter.MyViewHolder> {

    Context context;
    ArrayList<My_ticket> my_tickets;


    public Tiket_Adapter(Context c, ArrayList<My_ticket> p) {
        context = c;
        my_tickets = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_tiket_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // format currency indonesia
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        final String getUrl = my_tickets.get(position).getUrl_photo();
        Integer nominal = Integer.valueOf(my_tickets.get(position).getTotal_harga());

        holder.xname_order.setText(my_tickets.get(position).getNama_order());
        holder.xid_tiket.setText(my_tickets.get(position).getId_ticket());
        holder.xjumlah_order.setText(my_tickets.get(position).getJumlah_order() + " Tikets");
        holder.xtotal_harga.setText(formatRupiah.format((double)nominal));
        Picasso.with(context).load(getUrl).into(holder.xphototicket);

        final String getNamaOrder = my_tickets.get(position).getId_ticket();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetail = new Intent(context, TicketOrderDetailActv.class);
                gotomyticketdetail.putExtra("name_order", getNamaOrder);
                context.startActivity(gotomyticketdetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_tickets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xname_order, xjumlah_order, xtotal_harga,xid_tiket;
        ImageView xphototicket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xname_order = itemView.findViewById(R.id.xname_order);
            xjumlah_order = itemView.findViewById(R.id.xjumlah_order);
            xtotal_harga = itemView.findViewById(R.id.xtotal_harga);
            xphototicket = itemView.findViewById(R.id.xphototicket);
            xid_tiket = itemView.findViewById(R.id.xid_tiket);

        }
    }

}
