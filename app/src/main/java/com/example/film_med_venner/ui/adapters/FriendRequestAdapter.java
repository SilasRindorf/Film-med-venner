package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends BaseAdapter {
        private Context ctx;
        private List<FullProfileDTO> profileItems = new ArrayList<>();

        public FriendRequestAdapter(Context ctx, List<FullProfileDTO> profileItems) {
                this.ctx = ctx;
                this.profileItems = profileItems;
        }

        @Override
        public int getCount() {
                return profileItems.size();
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
                FullProfileDTO item = profileItems.get(position);
                if (gridView == null) {
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        gridView = inflater.inflate(R.layout.profile_friend_request_item, null);
                }

                TextView friend_request_name = gridView.findViewById(R.id.textView_fr_name);
                ImageView profilePicture = gridView.findViewById(R.id.imageView_profile);


                friend_request_name.setText(item.getName());
                Picasso.get().load(item.getPictureURL()).into(profilePicture);



                return gridView;
        }


        public void addItem(FullProfileDTO p) {
                profileItems.add(p);
                this.notifyDataSetChanged();
        }

        public void removeItem(int position) {
                profileItems.remove(position);
                this.notifyDataSetChanged();
        }
}
