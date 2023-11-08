package com.example.civiladvocacyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfficialViewHolder extends RecyclerView.ViewHolder{
    TextView officesNameOfficialEntry;
    TextView officialsNameOfficialEntry;
    ImageView imageOfficialEntry;
    public OfficialViewHolder(@NonNull View itemView) {
        super(itemView);
        officesNameOfficialEntry = itemView.findViewById(R.id.officesNameOfficialEntry);
        officialsNameOfficialEntry = itemView.findViewById(R.id.officialsNameOfficialEntry);
        imageOfficialEntry = itemView.findViewById(R.id.imageOfficialEntry);
    }
}
