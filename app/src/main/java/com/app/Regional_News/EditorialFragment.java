package com.app.Regional_News;

import static com.app.Regional_News.extra.UtilsApi.BASE_URL_API;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Regional_News.adapter.SearchNewslistAdapter;
import com.app.Regional_News.data.RN_Udata;
import com.app.Regional_News.data.Search_News_listfetch_data;
import com.app.Regional_News.data.Search_News_listfetch_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.SharedPrefManager;
import com.app.Regional_News.extra.UtilsApi;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialFragment extends Fragment {


    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    SearchNewslistAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String new_keyword = "Editorial";

    public EditorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sports, container, false);



        mApiService = UtilsApi.getAPIService();
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = rootView.findViewById(R.id.lyt_not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView



        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata(new_keyword);
            }
        });

        if (NetworkUtils.isConnected(getActivity())) {
            showProgress(true);
            getdata(new_keyword);
        } else {
            Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }








        return rootView;
    }

    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    private void getdata(String keyword) {
        mApiService.rnSearchNewslistRequest("search_news_list_show", keyword)
                .enqueue(new Callback<Search_News_listfetch_data>() {
                    @Override
                    public void onResponse(Call<Search_News_listfetch_data> call, Response<Search_News_listfetch_data> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful()) {
                            Log.e("msg", "" + response.code());
                            showProgress(false);
                            Search_News_listfetch_data degdata = response.body();
                            Log.e("msg2", degdata.getMsg());
                            if (degdata.getStatus().equals("1")) {
                                String error_message = degdata.getMsg();
                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
                                displayData(degdata.getSearch_news_list_show());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("msg1", "" + response.code());
                            Log.e("msg5", "" + call.request().url());
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Search_News_listfetch_data> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        showProgress(false);
                    }
                });
    }

    private void displayData(ArrayList<Search_News_listfetch_listdata> degree_list) {
        adapter = new SearchNewslistAdapter(getActivity(), degree_list);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getdata(new_keyword);// Refresh data when the fragment is visible again
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static class SettingFragment extends Fragment {


        SharedPrefManager sharedPrefManager;
        RN_Udata fp;
        public  static  String uid;
        private Switch mode_switch;
        private TextView modeStatus;
        RelativeLayout scholarship_lay,event_lay,sudoko_lay,quiz_lay,feedback_lay,profile_lay,savenews_lay,app_share_lay;
        TextView navUsername,navEmail,lang_status;
        ImageView navPic;
        Switch language_mode_switch;

        boolean isGujarati;
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
            fp = gson.fromJson(fdata, RN_Udata.class);

            // Find the views and set data
            navUsername = rootView.findViewById(R.id.tv_uname);
            navEmail = rootView.findViewById(R.id.tv_uemail);
            navPic = rootView.findViewById(R.id.imageView);
            RelativeLayout logoutnav = rootView.findViewById(R.id.logoutnav);
            mode_switch = rootView.findViewById(R.id.mode_switch);
            modeStatus = rootView.findViewById(R.id.modeStatus);
            scholarship_lay = rootView.findViewById(R.id.scholarship_lay);
            event_lay = rootView.findViewById(R.id.event_lay);
            sudoko_lay = rootView.findViewById(R.id.sudoko_lay);
            quiz_lay = rootView.findViewById(R.id.quiz_lay);
            feedback_lay = rootView.findViewById(R.id.feedback_lay);
            profile_lay = rootView.findViewById(R.id.profile_lay);
            savenews_lay = rootView.findViewById(R.id.savenews_lay);
            language_mode_switch = rootView.findViewById(R.id.language_mode_switch);
            lang_status = rootView.findViewById(R.id.lang_status);
            app_share_lay = rootView.findViewById(R.id.app_share_lay);

            getCurrentUserdata();


            boolean isGujarati = sharedPrefManager.getLanguagePreference();
            setAppLocale(isGujarati ? "gu" : "en");
            language_mode_switch.setChecked(isGujarati);

            // Set the language status text based on the language preference
// Set the language status text based on the language preference using if-else
            if (isGujarati) {
                lang_status.setText("ગુજરાતી ભાષા કરેલ છે");
            } else {
                lang_status.setText("English language is enabled");
            }

            language_mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Save the language preference
                    sharedPrefManager.saveLanguagePreference(SharedPrefManager.SP_LANGUAGE, isChecked);

                    // Set the app's locale to Gujarati if the switch is on, otherwise set it to English
                    setAppLocale(isChecked ? "gu" : "en");

                    // Recreate the activity to apply the language change
                    requireActivity().recreate();
                }
            });



            // Check the current UI mode and set the switch state accordingly
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                mode_switch.setChecked(true);
//                modeStatus.setText("ડાર્ક મોડ ચાલુ");
                modeStatus.setText(R.string.setting_darkmode); // Use string resource here

            } else {
                mode_switch.setChecked(false);
//                modeStatus.setText("ડાર્ક મોડ બંદ");
                modeStatus.setText(R.string.setting_lightmode); // Use string resource here

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


            app_share_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
                    i.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) +"\n https://drive.google.com/file/d/1pXmnfblXyN6mISViHcHcE-NM_OH5xg4w/view?usp=drive_link");
                    startActivity(i.createChooser(i, "Share Via"));
                }
            });



//            app_share_lay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "app-debug.apk");
//
//                    if (apkFile.exists()) {
////                        Toast.makeText(requireContext(), "APK File Path: " + apkFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
//
//                        // Generate a URI for the APK file using FileProvider
//                        Uri apkUri = FileProvider.getUriForFile(
//                                requireContext(),
//                                BuildConfig.APPLICATION_ID + ".provider",
//                                apkFile);
//
//                        // Create the share intent
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("application/vnd.android.package-archive");
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, apkUri);
//                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                        // Start the share activity
//                        startActivity(Intent.createChooser(shareIntent, "Share Via"));
//                    } else {
//                        // Handle the case where the APK file does not exist
//                        new AlertDialog.Builder(requireContext())
//                                .setTitle("File not found")
//                                .setMessage("The APK file could not be found. Please make sure it is saved in the correct location.")
//                                .setPositiveButton(android.R.string.ok, null)
//                                .show();
//                    }
//                }
//            });

            savenews_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(requireContext(), SavedNewsActivity.class);
                    startActivity(i);
                }
            });

            profile_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(requireContext(), ProfileUpdationActivation.class);
                    startActivity(i);
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

            quiz_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(requireContext(), QuizActivity.class);
                    startActivity(i);
                }
            });


            sudoko_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClassName("de.dlyt.yanndroid.sudoku", "de.dlyt.yanndroid.sudoku.SplashActivity_s");
                    startActivity(intent);
                }
            });


            feedback_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(requireContext(), FeedbackActivity.class);
                    startActivity(i);
                }
            });

            logoutnav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle(R.string.logouttite);

                    builder.setMessage(R.string.logoutdes);
                    builder.setPositiveButton(R.string.logoutok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_F_LOGIN, false);
                            Intent i = new Intent(requireContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.logoutcancel, new DialogInterface.OnClickListener() {
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

        private void setAppLocale(String localeCode) {
            Locale locale = new Locale(localeCode);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = new Configuration(resources.getConfiguration());
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }


        @Override
        public void onResume() {
            super.onResume();
            getCurrentUserdata(); // Refresh data when the fragment is visible again
        }

        private void getCurrentUserdata() {


            // DATA FETCHING FROM USER
            String fdata = sharedPrefManager.getFdata();
            Gson gson = new Gson();
            fp = gson.fromJson(fdata, RN_Udata.class);


            navUsername.setText(fp.getU_name());
            navEmail.setText(fp.getU_email());


            String c_pic = BASE_URL_API + fp.getU_pic();
            uid = fp.getU_id();
            Picasso.get()
                    .load(c_pic)
                    .resize(100, 100)
                    .centerCrop()
                    .into(navPic);
        }
    }
}

