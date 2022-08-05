package com.example.duofingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity implements ContinueReadingChapterSelectListener, LocationListener {

    Button topicSelect;
    RecyclerView continueReadingRV;
    ArrayList<ContinueReadingDataSource> continueReadingDataSource;

    RecyclerView dashBoardRankingRv;
    ArrayList<DashBoardRankingDataSourceSet> dashBoardRankingDataSource;

    DashBoardRankingAdapter dashBoardRankingAdapter;
    Button chapterSelect;
    Button dashboardDesign;
    Button quizPlay;

    TextView locationText;
    String currentEmail;
    String currentPassword;
    String currentCountry;

    List<Pair> countryRanks = new ArrayList<>();

    Chip heyUsername;


    private static final String TAG = "DB";

    /*
    location services stuff

     */

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] fineLocation = {Manifest.permission.ACCESS_FINE_LOCATION};

    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle extras = getIntent().getExtras();
        currentEmail = extras.getString("userEmail");
        currentPassword = extras.getString("password");

        heyUsername = findViewById(R.id.chipForProfile);

        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    if (Objects.equals(documentSnapshot.get("email"), currentEmail)
                            && Objects.equals(documentSnapshot.get("password"), currentPassword)) {
                        heyUsername.setText("Hello " + documentSnapshot.getString("userName"));
                        currentCountry = documentSnapshot.getString("country");
                        break;
                    }
                    //documentReference[0] = db.collection("users").document()
                }
            }


        });


        // Recycler View populate for continue Reading
        continueReadingDataSource = new ArrayList<>();

        continueReadingDataSource.add(new ContinueReadingDataSource("2/12"));
        continueReadingDataSource.add(new ContinueReadingDataSource("1/12"));
        continueReadingDataSource.add(new ContinueReadingDataSource("4/12"));
        continueReadingDataSource.add(new ContinueReadingDataSource("5/12"));
        continueReadingDataSource.add(new ContinueReadingDataSource("10/12"));

        continueReadingRV = findViewById(R.id.continueReadingRecycleView);
        continueReadingRV.setHasFixedSize(true);
        continueReadingRV.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
        continueReadingRV.setAdapter(new MyContinueReadingAdapter(continueReadingDataSource, DashboardActivity.this, this));


        // Recycle View Data for Ranking
        dashBoardRankingDataSource = new ArrayList<>();

        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Log.d(TAG, "THIS IS THE CURRENT COUNTRY: " + currentCountry);
                    if (Objects.equals(documentSnapshot.get("country"), currentCountry)) {
                        //dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet("Jai", "1", "12"))
                        //Log.d(TAG, "THIS IS THE CURRENT USER'S FULL NAME: " + documentSnapshot.getString("fullName"));
                        //Log.d(TAG, "THIS IS THE CURRENT USER'S FULL SCORE: " + documentSnapshot.getLong("userScore"));
                        countryRanks.add(new Pair((String) documentSnapshot.getString("fullName"), (Long) documentSnapshot.getLong("userScore")));
                    }
                }
            }

            Log.d(TAG, "CURRENT LIST: " + countryRanks );

            if (countryRanks != null) {
                Collections.sort(countryRanks, (a, b) -> b.score.compareTo(a.score));
                for (Integer i = 0; i < countryRanks.size(); i++) {
                    Pair current = countryRanks.get(i);
                    Integer rank = i + 1;

                    dashBoardRankingDataSource.add(new DashBoardRankingDataSourceSet(current.name, rank.toString(), i.toString()));
                }
                dashBoardRankingRv.getAdapter().notifyDataSetChanged();
            }
        });

        dashBoardRankingRv = findViewById(R.id.dashBoardRankingRecycleView);
        dashBoardRankingRv.setHasFixedSize(true);
        dashBoardRankingRv.setLayoutManager(new LinearLayoutManager(this));
        dashBoardRankingRv.setAdapter(new DashBoardRankingAdapter(dashBoardRankingDataSource, this));
        locationText = findViewById(R.id.location);


//        topicSelect = findViewById(R.id.topic_selection);
//        topicSelect.setOnClickListener(v -> openTopicSelectActivity(this));

//        chapterSelect = findViewById(R.id.chapter_selection);
//        chapterSelect.setOnClickListener(v -> openChaptersSelectActivity());
//
//        dashboardDesign = findViewById(R.id.dashboard_design);
//        dashboardDesign.setOnClickListener(v -> openDashboardDesignActivity());
//
//        quizPlay = findViewById(R.id.quiz_play);
//        quizPlay.setOnClickListener(v -> openQuizPlayActivity());


        /*
         * Getting permissions from the users
         */
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    fineLocation, 100);

        } else {
            getLocation();
        }
    }

    public void openTopicSelectActivity(View view) {
        Intent intent = new Intent(this, TopicSelectionActivity.class);
        startActivity(intent);
    }

    public void openChaptersSelectActivity() {
        Intent intent = new Intent(this, ChapterSelectionActivity.class);
        startActivity(intent);
    }

    public void openDashboardDesignActivity(View view) {
        Intent intent = new Intent(this, DashDesignActivity.class);
        startActivity(intent);
    }

    public void openQuizPlayActivity(View view) {
        Intent intent = new Intent(this, QuizStartActivity.class);
        startActivity(intent);
    }

    private void getLocation() {
        try {
            lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, DashboardActivity.this);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectChapter(ContinueReadingDataSource link) {
        this.openChaptersSelectActivity();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            //System.out.println("THIS IS THE CURRENT USER ADDRESS" + addressList.get(0).getAddressLine(0));
            //locationText.setText(addressList.get(0).getAddressLine(0));
            Address addy = addressList.get(0);

            currentCountry = addy.getCountryName();
            //locationText.setText("Current Country: " + addy.getCountryName() + "\n" + "current city: " + addy.getLocality());
            final String[] Id = new String[1];

            // Update the current user's country
            db.collection("users").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (Objects.equals(documentSnapshot.get("email"), currentEmail)
                        && Objects.equals(documentSnapshot.get("password"), currentPassword)) {
                            Id[0] = documentSnapshot.getId();
                            break;
                        }
                        //documentReference[0] = db.collection("users").document()
                    }
                }

                DocumentReference dr = db.collection("users").document(Id[0]);
                dr.update("country", addy.getCountryName());
                dr.update("city", addy.getLocality());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}