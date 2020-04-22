package win.shopping;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static win.shopping.DBqueries.categoryModelList;

import static win.shopping.DBqueries.lists;

import static win.shopping.DBqueries.loadCategories;
import static win.shopping.DBqueries.loadCategoryName;
import static win.shopping.DBqueries.loadFragmentData;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView categoryRecycleView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homepageRecycleView;
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true){
            noInternetConnection.setVisibility(View.GONE);
            MainActivity.drawer.setDrawerLockMode(0);


            categoryRecycleView = view.findViewById(R.id.categoty_recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecycleView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecycleView.setAdapter(categoryAdapter);

            if(categoryModelList.size() == 0){
                loadCategories(categoryAdapter,getContext());
            }else {
                categoryAdapter.notifyDataSetChanged();
            }

            ////////////////////////////////
            homepageRecycleView = view.findViewById(R.id.home_page_recycleview);
            LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
            testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homepageRecycleView.setLayoutManager(testingLayoutManager);

            if(lists.size() == 0){
                loadCategoryName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                adapter = new HomePageAdapter(lists.get(0));
                loadFragmentData(adapter,getContext(),0,"Home");
            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }

            homepageRecycleView.setAdapter(adapter);
        }else{
            MainActivity.drawer.setDrawerLockMode(1);
            Glide.with(this).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);

        }
        return view;
    }








}
