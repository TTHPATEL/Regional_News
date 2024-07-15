package com.app.Regional_News.ui.gallery;

import static com.app.Regional_News.extra.UtilsApi.BASE_URL_API;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.Regional_News.R;
import com.app.Regional_News.data.Udata;
import com.app.Regional_News.databinding.ActivityMainBinding;
import com.app.Regional_News.extra.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class SettingFragment extends Fragment {


    SharedPrefManager sharedPrefManager;
    Udata fp;
    public  static  String uid;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        String fdata = sharedPrefManager.getFdata();
        Gson gson = new Gson();
        fp = gson.fromJson(fdata, Udata.class);

        // Find the views and set data
        TextView navUsername = rootView.findViewById(R.id.tv_uname);
        TextView navEmail = rootView.findViewById(R.id.tv_uemail);
        ImageView navPic = rootView.findViewById(R.id.imageView);

        navUsername.setText(fp.getU_name());
        navEmail.setText(fp.getU_email());

        String c_pic = BASE_URL_API + fp.getU_pic();
        uid = fp.getU_id();
        Picasso.get()
                .load(c_pic)
                .resize(100, 100)
                .centerCrop()
                .into(navPic);
        return rootView;
    }
}
