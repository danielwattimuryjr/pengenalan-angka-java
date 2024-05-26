package com.example.pengenalanangka.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.view_pager.LearnViewPagerItem;

import java.util.ArrayList;

public class LearnAngkaAdapter extends RecyclerView.Adapter<LearnAngkaAdapter.ViewHolder>{
    ArrayList<LearnViewPagerItem> viewPagerItemArrayList;

    private onTtsClickListener listener;

    public LearnAngkaAdapter(ArrayList<LearnViewPagerItem> viewPagerItemArrayList) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lear_view_pager_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearnViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);
        holder.textView.setText(viewPagerItem.item);
    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageButton textToSpeechBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.displayText);
            textToSpeechBtn = itemView.findViewById(R.id.textToSpeechBtn);

            textToSpeechBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onTtsClick(position);
                }
            });
        }
    }

    public interface onTtsClickListener {
        void onTtsClick(int position);
    }

    public void onTtsClickListener(onTtsClickListener listener) {
        this.listener = listener;
    }
}
