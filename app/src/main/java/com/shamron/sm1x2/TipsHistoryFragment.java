package com.shamron.sm1x2;
/*
 *****created by shamron on 11/8/2019 at 03:33 PM*****
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.shamron.sm1x2.Adapter.TipsAdapter;
import com.shamron.sm1x2.Util.Util;
import com.shamron.sm1x2.model.Tips;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class TipsHistoryFragment extends Fragment {

    /*
    * Var*/
    private FirebaseFirestore db;
    private List<Tips> mTipsList;
    private TipsAdapter adapter;
    private RecyclerView history_tips_recycler;
    private TextView tip_history_date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SpinKitView spin_history_tips_loader;
    private String yesterday;
    private SwipeRefreshLayout historySwipeRefreshLayout;
    private LinearLayout try_again_Layout;
    private Button btn_try_again;

    public TipsHistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tip_history_fragment, container, false);

        //init
        db = FirebaseFirestore.getInstance();
        spin_history_tips_loader = view.findViewById(R.id.spin_history_tips_loader);
        tip_history_date = view.findViewById(R.id.tip_history_date);
        history_tips_recycler = view.findViewById(R.id.history_tips_recycler);
        history_tips_recycler.setHasFixedSize(true);
        history_tips_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        historySwipeRefreshLayout = view.findViewById(R.id.swipe_history);
        try_again_Layout = view.findViewById(R.id.layout_noInternet_history);
        btn_try_again = view.findViewById(R.id.btn_try_again_history);

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        historySwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        historySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                historySwipeRefreshLayout.setRefreshing(false);
            }
        });

        historySwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadData();
                historySwipeRefreshLayout.setRefreshing(false);
            }
        });
        //
        mTipsList = new ArrayList<>();
        adapter = new TipsAdapter(getContext(),mTipsList);

        /*Date
        * Get previous day*/
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        yesterday = dateFormat.format(calendar.getTime());

        tip_history_date.setText(String.format("Yesterday: %s", yesterday));


        /*Load data*/
        loadData();

        return view;
    }

    private void loadData() {

        if (Util.isConnectedToInternet(Objects.requireNonNull(getContext())))
        {
            try_again_Layout.setVisibility(View.GONE);
            db.collection("Tips").whereEqualTo("date", yesterday)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                            @Nullable FirebaseFirestoreException e)
                        {

                            if (!Objects.requireNonNull(queryDocumentSnapshots).isEmpty())
                            {
                                //hide loader
                                spin_history_tips_loader.setVisibility(View.GONE);

                                //clear old list
                                mTipsList.clear();

                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d: list)
                                {
                                    Tips tips = d.toObject(Tips.class);
                                    mTipsList.add(tips);
                                }
                            }

                            history_tips_recycler.setAdapter(adapter);
                        }
                    });
        }else
        {
            try_again_Layout.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connection failed!!" +
                    "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
        }
    }
}
