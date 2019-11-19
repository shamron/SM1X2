package com.shamron.sm1x2;
/*
 *****created by shamron on 11/17/2019 at 11:56 PM*****
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.shamron.sm1x2.Adapter.GoalScorerAdapter;
import com.shamron.sm1x2.Util.Util;
import com.shamron.sm1x2.model.GoalScorers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GSFragment extends Fragment {

    private FirebaseFirestore db;
    private List<GoalScorers> goalScorersList;
    private GoalScorerAdapter goalScorerAdapter;
    private RecyclerView gs_RecyclerView;
    private SpinKitView spinKitView;
    private SimpleDateFormat ft;
    private Date date;
    private TextView txt_date;
    private SwipeRefreshLayout gsSwipeRefreshLayout;
    private LinearLayout layout_noInternet;
    private Button btn_try_again;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*Get date*/
        ft = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();

        /*init FireBase*/
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_gs, container, false);

        /*init*/
        layout_noInternet = view.findViewById(R.id.layout_gs_noInternet);
        gs_RecyclerView = view.findViewById(R.id.gs_recycler);
        gs_RecyclerView.setHasFixedSize(true);
        gs_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spinKitView = view.findViewById(R.id.spin_gs_loader);
        gsSwipeRefreshLayout = view.findViewById(R.id.swipe_gs);
        btn_try_again = view.findViewById(R.id.btn_try_again_gs);
        txt_date = view.findViewById(R.id.gs_date);

        /*swipe to refresh layout*/
        gsSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        gsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                gsSwipeRefreshLayout.setRefreshing(false);
            }
        });

        gsSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadData();
                gsSwipeRefreshLayout.setRefreshing(false);
            }
        });

        goalScorersList = new ArrayList<>();
        goalScorerAdapter = new GoalScorerAdapter(getContext(), goalScorersList);

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        /*Load Data from FireBase*/
        loadData();

        return view;
    }

    private void loadData() {
        String today = ft.format(date);
        txt_date.setText(today);

        if (Util.isConnectedToInternet(getContext()))
        {
            layout_noInternet.setVisibility(View.GONE);
            /*get goal scorers*/
            db.collection("GoalScorers")
                    .whereEqualTo("date", today)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if ( !queryDocumentSnapshots.isEmpty()) {
                                //hide spinkit
                                spinKitView.setVisibility(View.GONE);

                                //clear old list first
                                goalScorersList.clear();

                                //add fresh data to list
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    GoalScorers goalScorers = d.toObject(GoalScorers.class);
                                    goalScorersList.add(goalScorers);
                                }
                            }else
                            {
                                Snackbar.make(getView(), "Today prediction are not available yet\n" +
                                        "Check again later", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }
                            //set Adapter
                            gs_RecyclerView.setAdapter(goalScorerAdapter);
                        }
                    });

        }else{
            layout_noInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connection failed!!" +
                    "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
        }
    }
}

