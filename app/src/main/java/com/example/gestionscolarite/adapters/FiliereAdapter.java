package com.example.gestionscolarite.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.activities.FilieresActivity;
import com.example.gestionscolarite.activities.UpdateFiliereActivity;
import com.example.gestionscolarite.controllers.FiliereRepo;

import java.util.ArrayList;

public class FiliereAdapter extends RecyclerView.Adapter<FiliereAdapter.MyViewHolder> {

    private Context context;
    private ArrayList filieres_id, filiere_nom, modules_count;
    Activity activity;

    int position;

    public FiliereAdapter(Activity activity, Context context, ArrayList filieres_id, ArrayList filiere_nom, ArrayList modules_count) {
        this.activity = activity;
        this.context = context;
        this.filieres_id = filieres_id;
        this.filiere_nom = filiere_nom;
        this.modules_count = modules_count;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.filiere_row, parent, false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;

//        holder.filiere_id.setText(String.valueOf(filieres_id.get(position)));
        holder.modulesCountTxt.setText(String.valueOf(modules_count.get(position)));
        holder.filiere_nom.setText(String.valueOf(filiere_nom.get(position)));
        holder.mainFiliereLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateFiliereActivity.class);
                intent.putExtra("idFiliere", String.valueOf(filieres_id.get(position)));
                intent.putExtra("nomFiliere", String.valueOf(filiere_nom.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.filiereDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiliereRepo filiereRepo = new FiliereRepo(context);
                long deleted =  filiereRepo.destroy(filieres_id.get(position).toString());
                if (deleted == -1){
                    Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
                activity.recreate();
            }
        });


    }

    @Override
    public int getItemCount() {
        return filieres_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView filiere_id, filiere_nom, modulesCountTxt;

        ImageButton filiereDeleteBtn;

        LinearLayout mainFiliereLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            filiere_nom = itemView.findViewById(R.id.filiereNomTxt);
            modulesCountTxt = itemView.findViewById(R.id.modulesCountTxt);
            mainFiliereLayout = itemView.findViewById(R.id.mainFiliereLayout);
            filiereDeleteBtn = itemView.findViewById(R.id.filiereDeleteBtn);
        }
    }

}
