package com.app.Regional_News.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.Regional_News.QuizWebViewActivity;
import com.app.Regional_News.R;
import com.app.Regional_News.data.Quiz_listdata;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private final ArrayList<Quiz_listdata> clist;
    private final Context mContext;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_quiz_name,tv_quiz_from_date,tv_quiz_end_date;
        public LinearLayout quiz_list_layout;

        public ViewHolder(View v) {
            super(v);
            tv_quiz_name = v.findViewById(R.id.tv_quiz_name);
            quiz_list_layout = v.findViewById(R.id.quiz_list_layout);
            tv_quiz_from_date = v.findViewById(R.id.tv_quiz_from_date);
            tv_quiz_end_date = v.findViewById(R.id.tv_quiz_end_date);
        }
    }

    // Provide a suitable constructor
    public QuizAdapter(Context context, ArrayList<Quiz_listdata> data) {
        this.clist = data;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_quiz, parent, false);

        QuizAdapter.ViewHolder vh = new QuizAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(QuizAdapter.ViewHolder holder, int position) {
        // Get element from arraylist at this position and replace the contents of the view with that element
        final Quiz_listdata data = clist.get(position);
        holder.tv_quiz_name.setText(data.getQuiz_name());
        holder.tv_quiz_from_date.setText(data.getQuiz_start_date());
        holder.tv_quiz_end_date.setText(data.getQuiz_end_date());


        holder.quiz_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext, QuizWebViewActivity.class);
                i.putExtra("Quiz_Link",data.getQuiz_link());


                mContext.startActivity(i);

//                FuelPlaceFragment docofragemnt=new FuelPlaceFragment();
//                Bundle arguments = new Bundle();
//                arguments.putString("cat_id",data.getF_t_c_id());
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
