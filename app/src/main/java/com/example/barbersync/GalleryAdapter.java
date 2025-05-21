/****************************************
 Fichier : GalleryAdater.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBSER2 (détail d'un coiffeur) - affiche la galerie
 Date : 2025-05-17


 Vérification :
 2025-05-20     Nicolas Beaudoin        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Samit Adelyar           ajout de commentaires
 =========================================================
 ****************************************/

package com.example.barbersync;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Coiffeur coiffeur;
    private Context context;

    public GalleryAdapter(Coiffeur c , Context context) {
        this.coiffeur = c;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_photo, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        //construire url
        String baseUrl = "http://192.168.11.212:5000/galeries/";
        String encodedName = android.net.Uri.encode(coiffeur.getName());
        String encodedFileName = android.net.Uri.encode(coiffeur.getPhotos().get(position).getNomFichierImage());
        String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

        Log.d(TAG, "URL encodée de l'image: " + fullUrl);

        //utilisation de glide pour prendre image
        Glide.with(context)
                .load(fullUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.scissors)
                .into(holder.imageViewGalleryPhoto);
    }

    @Override
    public int getItemCount() {
        return coiffeur.getPhotos().size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGalleryPhoto;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGalleryPhoto = itemView.findViewById(R.id.imageViewGalleryPhoto);
        }
    }
}
