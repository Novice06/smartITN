package com.itn.smartitn.events;

import com.itn.smartitn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClick(Event event);
    }

    private final List<Event> events;
    private final OnClickListener listener;

    public EventAdapter(List<Event> events, OnClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.tvTitre.setText(event.titre);
        holder.tvDate.setText("📅 " + event.dateFormatee());
        holder.tvLieu.setText("📍 " + event.lieu);
        holder.itemView.setOnClickListener(v -> listener.onClick(event));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitre, tvDate, tvLieu;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tv_titre);
            tvDate  = itemView.findViewById(R.id.tv_date);
            tvLieu  = itemView.findViewById(R.id.tv_lieu);
        }
    }
}
