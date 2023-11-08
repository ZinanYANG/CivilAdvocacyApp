package com.example.civiladvocacyapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
public class OfficialAdapter extends RecyclerView.Adapter<OfficialViewHolder>{

    private final List<Official> OfficialList;
    private final MainActivity mainActivity;
    private TextView officesNameOfficialEntry;
    private TextView officialsNameOfficialEntry;
    private ImageView imageOfficialEntry;
    private Picasso picasso = Picasso.get();

    public OfficialAdapter(List<Official> OfficialList, MainActivity mainActivity) {
        this.OfficialList = OfficialList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OfficialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official_entry, parent, false);
        itemView.setOnClickListener((View.OnClickListener) mainActivity);
        itemView.setOnLongClickListener((View.OnLongClickListener) mainActivity);
        return new OfficialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialViewHolder holder, int position) {
        Official official = OfficialList.get(position);
        holder.officesNameOfficialEntry.setText(official.getOfficeTitle());
        holder.officialsNameOfficialEntry.setText(official.getName());
        String url = official.getPhotoUrl() != null ? official.getPhotoUrl().toString() : null;
        Log.d("PICASSO_DEBUG", "Dynamic URL: " + url);
        Log.d("PICASSO_DEBUG", "Hardcoded URL: https://bioguide.congress.gov/bioguide/photo/D/D000622.jpg");
//        Dynamic URL: http://bioguide.congress.gov/bioguide/photo/D/D000563.jpg
//        Hardcoded URL: https://bioguide.congress.gov/bioguide/photo/D/D000622.jpg
//        difference : https and http
        if (url != null && url.startsWith("http:")) {
            url = url.replace("http:", "https:");
        }
        if (url != null && !url.trim().isEmpty()) {
            picasso.load(url)
                    .error(R.drawable.missing)
                    .into(holder.imageOfficialEntry);
        } else {
            holder.imageOfficialEntry.setImageResource(R.drawable.missing);
        }
    }
    public void updateData(ArrayList<Official> newOfficials) {
        this.OfficialList.clear();
        this.OfficialList.addAll(newOfficials);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return OfficialList.size();
    }
    public Official getArtwork(int position) {
        return OfficialList.get(position);
    }
}