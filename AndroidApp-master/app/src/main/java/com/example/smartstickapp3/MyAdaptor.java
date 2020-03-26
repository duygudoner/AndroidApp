package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    Context context;  // layout inflater service erişmeyi sağlayacak
    LayoutInflater inflater;
    ArrayList<NavigationDrawerItem> mDataList;

    MyAdaptor(Context c,ArrayList<NavigationDrawerItem> data){
        this.context=c;
        this.inflater = LayoutInflater.from(context);
        mDataList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ilk inflate işlemleri burada yapılıyor.
        View v = inflater.inflate(R.layout.oneline,parent,false);
        MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // hangi eleman tıklandıysa ilgili view a atanacak örneğin 4. eleman tıklandı

        NavigationDrawerItem tiklanılan = mDataList.get(position);
        holder.setData(tiklanılan,position);


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView baslik;
        ImageView resim;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            baslik = itemView.findViewById(R.id.title);
            resim = itemView.findViewById(R.id.ImgIcon);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(context,baslik.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(NavigationDrawerItem tiklanılan, int position) {
            this.baslik.setText(tiklanılan.getBaslik());
            this.resim.setImageResource(tiklanılan.getResimID());
        }
    }
}
