package com.mobilityhackathon.doggohealth.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.mobilityhackathon.doggohealth.DogPartyActivity;
import com.mobilityhackathon.doggohealth.R;
import com.mobilityhackathon.doggohealth.RequestVetActivity;
import com.mobilityhackathon.doggohealth.ScheduleActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private int pageInt = 0;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment(index);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }
    NotificationManager notificationManager;

    private PlaceholderFragment(){}

    private PlaceholderFragment(int i) {
        pageInt = i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root;

        switch (pageInt) {
            case 1:
                root = inflater.inflate(R.layout.fragment_health_risks, container, false);

                break;
            // here we setup which link to download
            case 2:
                root = inflater.inflate(R.layout.fragment_main, container, false);
//        final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

                WebView wv = root.findViewById(R.id.webview);

                wv.setWebViewClient(new WebViewClient()
                {
                    // Override URL
                    public boolean shouldOverrideUrlLoading(WebView view, String url)
                    {
                        if (url.equalsIgnoreCase("file:///android_asset/activity_a://a")){
                            Intent intent=new Intent(getContext(), RequestVetActivity.class);
                            startActivity(intent);
                        } else if (url.equalsIgnoreCase("file:///android_asset/activity_b://b")){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:911"));
                            startActivity(intent);
                        } else if (url.equalsIgnoreCase("file:///android_asset/activity_c://c")){
                            Intent intent = new Intent(getContext(), ScheduleActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), DogPartyActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

                wv.getSettings().setJavaScriptEnabled(true);
                wv.getSettings().setLoadWithOverviewMode(true);
                wv.getSettings().setUseWideViewPort(true);
                wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                wv.setScrollbarFadingEnabled(false);
                wv.clearCache(true);

                String website = "file:///android_asset/doggohealthmain.html";



                wv.loadUrl(website);
                break;
            case 3:
                root = inflater.inflate(R.layout.fragment_dog_info, container, false);
                break;
                default: root = inflater.inflate(R.layout.fragment_health_risks, container, false);
        }

//        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "266666")
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(266666, builder.build());


        return root;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "DoggoChannel";
            String description = "Doggo Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("266666", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}