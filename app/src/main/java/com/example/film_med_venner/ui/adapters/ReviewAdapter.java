package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IReview;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private Context ctx;
    private List<IReview> reviewItems;

    public ReviewAdapter(Context ctx, List<IReview> reviewItems) {
        this.ctx = ctx;
        this.reviewItems = reviewItems;
    }

    @Override
    public int getCount() {
        return reviewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        IReview item = reviewItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.profile_review_item, null);
        }

        TextView reviewText = gridView.findViewById(R.id.reviewtext);

        reviewText.setText(((Review) item).getReview());

        return gridView;
    }

}