/****************************************
 Fichier : CoiffeurListAdapter.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBSER1 (lister les coiffeurs)
 Date : 2025-05-11


 Vérification :
 2025-05-12     Nicolas Beaudoin        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-16     Yassine Abide           correction du bug Glide
 2025-05-20     Samit Adelyar           ajout de commentaires
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class CoiffeurListAdapter extends RecyclerView.Adapter<CoiffeurListAdapter.CoiffeurViewHolder> {

    private static final String TAG = "CoiffeurAdapter";
    private final Context context;
    private final List<Coiffeur> coiffeurs;

    public CoiffeurListAdapter(Context context, List<Coiffeur> coiffeurs) {
        this.context = context;
        this.coiffeurs = coiffeurs;
        Log.d(TAG, "Adapter créé avec " + coiffeurs.size() + " coiffeurs");
    }

    @NonNull
    @Override
    public CoiffeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coiffeur_list, parent, false);
        return new CoiffeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoiffeurViewHolder holder, int position) {
        try {
            Coiffeur coiffeur = coiffeurs.get(position);
            List<Photo> photos = coiffeur.getPhotos();
            Log.d(TAG, "Binding coiffeur: " + coiffeur.getName());

            // Définir d'abord le nom du coiffeur
            holder.nomCoiffeur.setText(coiffeur.getName());

            // Définir une image par défaut
            holder.photoCoiffeur.setImageResource(R.drawable.scissors);

            // Vérifier si la liste de photos n'est pas vide
            if (photos != null && !photos.isEmpty()) {
                Photo photo = photos.get(0);
                String nomFichier = photo.getNomFichierImage();

                // Vérification supplémentaire pour le nom du fichier
                if (nomFichier == null || nomFichier.isEmpty()) {
                    Log.e(TAG, "Nom de fichier vide pour " + coiffeur.getName());
                    return;
                }

                // Log du nom du fichier pour déboguer
                Log.d(TAG, "Nom du fichier image: " + nomFichier);

                // Construction de l'URL en encodant les parties
                String baseUrl = "http://192.168.2.160:5000/galeries/";
                String encodedName = android.net.Uri.encode(coiffeur.getName());
                String encodedFileName = android.net.Uri.encode(nomFichier);
                String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

                Log.d(TAG, "URL encodée de l'image: " + fullUrl);

                // Glide pour reprendre l'image
                Glide.with(context.getApplicationContext())
                        .load(fullUrl)
                        .override(500, 500)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.scissors)
                        .error(R.drawable.scissors)
                        .into(holder.photoCoiffeur);
            } else {
                Log.w(TAG, "Pas de photos disponibles pour: " + coiffeur.getName());
            }

            // Ajouter un listener pour naviguer vers la page des disponibilités
            holder.cardView.setOnClickListener(v -> {
                try {
                    Log.d(TAG, "Click sur le coiffeur: " + coiffeur.getName());
                    Intent intent = new Intent(context, coiffeurDetailActivity.class);
                    intent.putExtra("coiffeur", coiffeur);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Erreur lors de la navigation vers les disponibilités: " + e.getMessage(), e);
                    Toast.makeText(context, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du binding: " + e.getMessage(), e);
            holder.nomCoiffeur.setText("Erreur: " + e.getMessage());
            holder.photoCoiffeur.setImageResource(R.drawable.scissors);
        }
    }

    @Override
    public int getItemCount() {
        return coiffeurs.size();
    }

    public static class CoiffeurViewHolder extends RecyclerView.ViewHolder {
        TextView nomCoiffeur;
        CardView cardView;
        ImageView photoCoiffeur; // Type ImageView déclaré

        public CoiffeurViewHolder(@NonNull View itemView) {
            super(itemView);
            nomCoiffeur = itemView.findViewById(R.id.nomCoiffeur);
            photoCoiffeur = itemView.findViewById(R.id.photo);

            // Vérifier si l'ImageView a été correctement trouvé
            if (photoCoiffeur == null) {
                Log.e(TAG, "ImageView avec id 'photo' non trouvé dans le layout!");
            } else {
                // Définir des paramètres de base pour s'assurer que l'image s'affichera
                photoCoiffeur.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoCoiffeur.setAdjustViewBounds(true);
            }

            cardView = (CardView) itemView;
        }
    }
}