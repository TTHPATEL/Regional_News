    package com.app.Regional_News.ui.gallery;

    import static com.app.Regional_News.extra.UtilsApi.BASE_URL_API;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.res.Configuration;
    import android.content.res.Resources;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatDelegate;
    import androidx.core.content.FileProvider;
    import androidx.fragment.app.Fragment;

    import android.os.Environment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.Switch;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.app.Regional_News.BuildConfig;
    import com.app.Regional_News.EventActivity;
    import com.app.Regional_News.FeedbackActivity;
    import com.app.Regional_News.LoginActivity;
    import com.app.Regional_News.ProfileUpdationActivation;
    import com.app.Regional_News.QuizActivity;
    import com.app.Regional_News.R;
    import com.app.Regional_News.SavedNewsActivity;
    import com.app.Regional_News.ScholarshipActivity;
    import com.app.Regional_News.data.Udata;
    import com.app.Regional_News.extra.SharedPrefManager;
    import com.google.gson.Gson;
    import com.squareup.picasso.Picasso;

    import java.io.File;
    import java.util.Locale;


    public class SettingFragment extends Fragment {


        SharedPrefManager sharedPrefManager;
        Udata fp;
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
            fp = gson.fromJson(fdata, Udata.class);

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
                    i.putExtra(Intent.EXTRA_SUBJECT, "Regional News App");
                    i.putExtra(Intent.EXTRA_TEXT, "Regional News App \n https://drive.google.com/file/d/1pXmnfblXyN6mISViHcHcE-NM_OH5xg4w/view?usp=drive_link");
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
            fp = gson.fromJson(fdata, Udata.class);


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
