package com.example.movielistassignment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movielistassignment.ItemClickListener;
import com.example.movielistassignment.R;
import com.example.movielistassignment.helpers.Endpoints;
import com.example.movielistassignment.model.Movie;

import java.util.ArrayList;

public class MoviesListRecyclerViewAdapter extends RecyclerView.Adapter<MoviesListRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Movie> arrayList;
    ItemClickListener itemClickListener;

    public MoviesListRecyclerViewAdapter(Context context, ArrayList<Movie> arrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = arrayList.get(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewDate.setText(movie.getRelease_date());
        String voteAverage = movie.getVote_average() + "/10";
        holder.textViewVoteAverage.setText(voteAverage);

        String posterUrl = Endpoints.getImageUrl(movie.getPoster_path());
        Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .into(holder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return (arrayList == null) ? 0 : arrayList.size();
    }

    public void updateData(ArrayList<Movie> arrayList) {
        Log.d("TAG", "Inside updateDAta of Adapter");
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewPoster;
        TextView textViewTitle, textViewDate, textViewVoteAverage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPoster = itemView.findViewById(R.id.image_view_poster);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(view, getAdapterPosition());
        }
    }
}
