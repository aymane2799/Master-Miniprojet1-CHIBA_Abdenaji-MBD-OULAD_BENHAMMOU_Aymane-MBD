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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.activities.UpdateEtudiantActivity;
import com.example.gestionscolarite.activities.UpdateFiliereActivity;
import com.example.gestionscolarite.controllers.EtudiantRepo;
import com.example.gestionscolarite.controllers.FiliereRepo;

import java.util.ArrayList;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.MyViewHolder> {

    private Context context;
    private ArrayList etudiant_id, etudiant_cne, etudiant_nom, etudiant_prenom, etudiant_niveau, etudiant_filiere;
    Activity activity;

    int position;

    public EtudiantAdapter( Activity activity, Context context, ArrayList etudiant_id, ArrayList etudiant_cne, ArrayList etudiant_nom, ArrayList etudiant_prenom, ArrayList etudiant_niveau, ArrayList etudiant_filiere) {
        this.context = context;
        this.activity = activity;
        this.etudiant_id = etudiant_id;
        this.etudiant_cne = etudiant_cne;
        this.etudiant_nom = etudiant_nom;
        this.etudiant_prenom = etudiant_prenom;
        this.etudiant_niveau = etudiant_niveau;
        this.etudiant_filiere = etudiant_filiere;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.etudiant_row,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;

        holder.etudiant_cne.setText(String.valueOf(etudiant_cne.get(position)));
        holder.etudiant_nom.setText(String.valueOf(etudiant_nom.get(position)) + " " +String.valueOf(etudiant_prenom.get(position)));
        holder.etudiant_filiere.setText(etudiant_filiere.get(position).toString());
        holder.mainEtudiantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateEtudiantActivity.class);
                intent.putExtra("idEtudiant", String.valueOf(etudiant_id.get(position)));
                intent.putExtra("cneEtudiant", String.valueOf(etudiant_cne.get(position)));
                intent.putExtra("nomEtudiant", String.valueOf(etudiant_nom.get(position)));
                intent.putExtra("prenomEtudiant", String.valueOf(etudiant_prenom.get(position)));
                intent.putExtra("niveauEtudiant", String.valueOf(etudiant_niveau.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.etudiantDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtudiantRepo etudiantRepo = new EtudiantRepo(context);
                long deleted =  etudiantRepo.destroy(etudiant_id.get(position).toString());
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
        return etudiant_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView etudiant_cne, etudiant_nom, etudiant_prenom, etudiant_niveau, etudiant_filiere;
        ImageButton etudiantDeleteBtn;
        LinearLayout mainEtudiantLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            etudiant_cne = itemView.findViewById(R.id.etudiant_cne);
            etudiant_nom = itemView.findViewById(R.id.etudiant_nom);
            etudiant_filiere = itemView.findViewById(R.id.etudiant_filiere);

            mainEtudiantLayout = itemView.findViewById(R.id.mainEtudiantLayout);
            etudiantDeleteBtn = itemView.findViewById(R.id.etudiantDeleteBtn);

        }
    }
}
