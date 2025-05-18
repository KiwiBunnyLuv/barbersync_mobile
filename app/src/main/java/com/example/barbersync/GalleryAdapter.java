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
        // Format rating and reviews text based on your Coiffeur object structure
        //textViewRating.setText(coiffeur.getRating() + " ★★★★☆ (" + coiffeur.getNumberOfReviews() + ")");

        String baseUrl = "http://192.168.2.160:5000/galeries/";
        String encodedName = android.net.Uri.encode(coiffeur.getName());
        String encodedFileName = android.net.Uri.encode(coiffeur.getPhotos().get(position).getNomFichierImage());
        String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

        Log.d(TAG, "URL encodée de l'image: " + fullUrl);

        // Configuration plus stricte de Glide
        Glide.with(context)
                .load(fullUrl)// Dimensions spécifiques
                .centerCrop()        // Mode d'échelle
                .diskCacheStrategy(DiskCacheStrategy.NONE)  // Désactiver le cache
                .skipMemoryCache(true)                      // Désactiver le cache mémoire
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
