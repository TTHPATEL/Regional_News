package com.app.Regional_News.ui.gallery;

import static com.app.Regional_News.extra.UtilsApi.BASE_URL_API;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Regional_News.EventActivity;
import com.app.Regional_News.LoginActivity;
import com.app.Regional_News.R;
import com.app.Regional_News.ScholarshipActivity;
import com.app.Regional_News.SudokuActivity;
import com.app.Regional_News.data.Udata;
import com.app.Regional_News.databinding.ActivityMainBinding;
import com.app.Regional_News.extra.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class SettingFragment extends Fragment {


    SharedPrefManager sharedPrefManager;
    Udata fp;
    public  static  String uid;
    private Switch mode_switch;
    private TextView modeStatus;
    RelativeLayout scholarship_lay,event_lay,sudoko_lay;

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
        RelativeLayout logoutnav = rootView.findViewById(R.id.logoutnav);
        mode_switch = rootView.findViewById(R.id.mode_switch);
        modeStatus = rootView.findViewById(R.id.modeStatus);
        scholarship_lay = rootView.findViewById(R.id.scholarship_lay);
        event_lay = rootView.findViewById(R.id.event_lay);
        sudoko_lay = rootView.findViewById(R.id.sudoko_lay);



        // Check the current UI mode and set the switch state accordingly
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            mode_switch.setChecked(true);
            modeStatus.setText("ડાર્ક મોડ ચાલુ");
        } else {
            mode_switch.setChecked(false);
            modeStatus.setText("ડાર્ક મોડ બંદ");
        }


        mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

            }
        });





        scholarship_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), ScholarshipActivity.class);
                startActivity(i);
            }
        });

        event_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), EventActivity.class);
                startActivity(i);
            }
        });

//        sudoko_lay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(requireContext(), SudokuActivity.class);
//                startActivity(i);
//            }
//        });


        sudoko_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("de.dlyt.yanndroid.sudoku", "de.dlyt.yanndroid.sudoku.SplashActivity_s");
                startActivity(intent);
            }
        });



        logoutnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Logout");
                builder.setMessage("Are u sure want to logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_F_LOGIN, false);
                        Intent i = new Intent(requireContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


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
