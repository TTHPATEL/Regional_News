package com.app.Regional_News.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback; // Import for Picasso


import com.app.Regional_News.R;
import com.app.Regional_News.data.News_listfetch_listdata;
import com.app.Regional_News.ui.NewsShowActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewslistAdapter extends RecyclerView.Adapter<NewslistAdapter.ViewHolder> {

    private final ArrayList<News_listfetch_listdata> clist;
    private final Context mContext;


    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //each data item is just a string in this case
        public TextView tv_m_name,tv_m_amount;
        public CardView cardview;
        public ImageView news_images;
        public LinearLayout news_list_layout;


        public ViewHolder(View v) {
            super(v);
            tv_m_name = v.findViewById(R.id.news_headline);
//            tv_m_amount = v.findViewById(R.id.news_description_1);
            cardview = v.findViewById(R.id.cardview);
            news_images = v.findViewById(R.id.news_images); // This is where news_images ImageView is initialized
            news_list_layout = v.findViewById(R.id.news_list_layout);

        }
    }

    //Provide a suitable constructor
    public NewslistAdapter(Context context, ArrayList<News_listfetch_listdata> songList) {
        this.clist = songList;
        this.mContext = context;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public NewslistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_newslist, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(NewslistAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        final News_listfetch_listdata song = clist.get(position);
        holder.tv_m_name.setText(song.getNews_headline());
//        holder.tv_m_amount.setText("Maintenance Rs."+song.getNews_des_1());



        // Load image using Picasso

        Picasso.get()
                .load(song.getNews_imgurl())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.news_images, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                        e.printStackTrace();
                    }
                });




        holder.news_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext, NewsShowActivity.class);
                i.putExtra("mm_id",song.getNews_id());
                i.putExtra("mm_m_year",song.getNews_headline());
                i.putExtra("getNews_id",song.getNews_id());
                i.putExtra("news_imgurl",song.getNews_imgurl());


                mContext.startActivity(i);

//                FuelPlaceFragment docofragemnt=new FuelPlaceFragment();
//                Bundle arguments = new Bundle();
//                arguments.putString("cat_id",song.getF_t_c_id());
//                docofragemnt.setArguments(arguments);
//                FragmentManager manager = ((AppCompatActivity)
//                        mContext).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,  docofragemnt);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


            }
        });


    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
