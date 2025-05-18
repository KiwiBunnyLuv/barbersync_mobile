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

                // Configuration plus stricte de Glide
                Glide.with(context.getApplicationContext()) // Utiliser le contexte de l'application pour éviter les fuites
                        .load(fullUrl)
                        .override(500, 500)  // Dimensions spécifiques
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .centerCrop()        // Mode d'échelle
                        .diskCacheStrategy(DiskCacheStrategy.NONE)  // Désactiver le cache
                        .skipMemoryCache(true)                      // Désactiver le cache mémoire
                        .placeholder(R.drawable.scissors)
                        .error(R.drawable.scissors)
                        .listener(new RequestListener<android.graphics.drawable.Drawable>() {
                            @Override
                            public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e, Object model,
                                                        Target<android.graphics.drawable.Drawable> target, boolean isFirstResource) {
                                Log.e(TAG, "❌ Échec de chargement pour l'image: " + fullUrl);
                                if (e != null) {
                                    Log.e(TAG, "Raison: " + e.getMessage());
                                    for (Throwable t : e.getRootCauses()) {
                                        Log.e(TAG, "Cause: " + t.getMessage());
                                    }
                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(android.graphics.drawable.Drawable resource, Object model,
                                                           Target<android.graphics.drawable.Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d(TAG, "✅ Image chargée avec succès pour: " + coiffeur.getName());

                                // Détails supplémentaires sur l'image chargée
                                Log.d(TAG, "Dimensions de l'image: " +
                                        resource.getIntrinsicWidth() + "x" + resource.getIntrinsicHeight() +
                                        " Alpha: " + resource.getAlpha());

                                return false;
                            }
                        })
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
                Log.e("CoiffeurAdapter", "❌ ERREUR CRITIQUE: ImageView avec id 'photo' non trouvé dans le layout!");
            } else {
                // Définir des paramètres de base pour s'assurer que l'image s'affichera
                photoCoiffeur.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoCoiffeur.setAdjustViewBounds(true);
            }

            cardView = (CardView) itemView;
        }
    }
}