package com.example.gestionscolarite.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.activities.UpdateFiliereActivity;
import com.example.gestionscolarite.activities.UpdateModuleActivity;

import java.util.ArrayList;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyViewHolder>{

    private Context context;
    private ArrayList module_id, module_nom;
    Activity activity;

    public ModuleAdapter(Activity activity, Context context, ArrayList module_id, ArrayList module_nom) {
        this.activity = activity;
        this.context = context;
        this.module_id = module_id;
        this.module_nom = module_nom;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.module_row, parent, false);

        return  new ModuleAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.module_id.setText(String.valueOf(module_id.get(position)));
        holder.module_nom.setText(String.valueOf(module_nom.get(position)));

        holder.mainModuleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateModuleActivity.class);
                intent.putExtra("idModule", String.valueOf(module_id.get(position)));
                intent.putExtra("nomModule", String.valueOf(module_nom.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {return module_id.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView module_id, module_nom;

        LinearLayout mainModuleLayout;

        ImageButton moduleDeleteBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            module_id = itemView.findViewById(R.id.moduleIdTxt);
            module_nom = itemView.findViewById(R.id.moduleNomTxt);
            mainModuleLayout = itemView.findViewById(R.id.mainModuleLayout);
            moduleDeleteBtn = itemView.findViewById(R.id.moduleDeleteBtn);


        }
    }

}

