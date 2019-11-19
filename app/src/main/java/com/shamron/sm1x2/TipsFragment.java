package com.shamron.sm1x2;

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
import com.shamron.sm1x2.Adapter.TipsAdapter;
import com.shamron.sm1x2.Util.Util;
import com.shamron.sm1x2.model.GoalScorers;
import com.shamron.sm1x2.model.Tips;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class TipsFragment extends Fragment {

    /*Var*/
    private FirebaseFirestore db;
    private List<Tips> tipsList;
    private TipsAdapter adapter;
    private RecyclerView tipsRecyclerView;
    private SpinKitView spinKitView;
    private SimpleDateFormat ft;
    private Date date;
    private TextView txt_date;
    private SwipeRefreshLayout tipsSwipeRefreshLayout;
    private LinearLayout layout_noInternet;
    private Button btn_try_again;


    public TipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Get date*/
        ft = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();

        /*init FireBase*/
        db = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tips, container, false);

        /*init*/
        layout_noInternet = view.findViewById(R.id.layout_noInternet);
        tipsRecyclerView = view.findViewById(R.id.tips_recycler);
        tipsRecyclerView.setHasFixedSize(true);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txt_date = view.findViewById(R.id.tip_date);
        spinKitView = view.findViewById(R.id.spin_tips_loader);
        tipsSwipeRefreshLayout = view.findViewById(R.id.swipe_tips);
        btn_try_again = view.findViewById(R.id.btn_try_again_tips);

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        tipsSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        tipsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                tipsSwipeRefreshLayout.setRefreshing(false);
            }
        });

        tipsSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadData();
                tipsSwipeRefreshLayout.setRefreshing(false);
            }
        });
        
        /**/
        tipsList = new ArrayList<>();
        adapter = new TipsAdapter(getContext(), tipsList);


        
        /*Load Data from FireBase*/
        loadData();


        /*return View*/
        return view;
    }

    private void loadData() {
        String today = ft.format(date);
        txt_date.setText(String.format("Today: %s", today));

        if (Util.isConnectedToInternet(getContext()))
        {
            layout_noInternet.setVisibility(View.GONE);
            db.collection("Tips")
                    .whereEqualTo("date", today)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if ( !queryDocumentSnapshots.isEmpty()) {
                                //hide spinkit
                                spinKitView.setVisibility(View.GONE);

                                //clear old list first
                                tipsList.clear();

                                //add fresh data to list
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    Tips t = d.toObject(Tips.class);
                                    tipsList.add(t);
                                }
                            }else
                            {
                                Snackbar.make(getView(), "Today prediction are not available yet\n" +
                                        "Check again later", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }
                            //set Adapter
                            tipsRecyclerView.setAdapter(adapter);
                        }
                    });


        }else
        {
            layout_noInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connection failed!!" +
                    "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
        }
    }

}
