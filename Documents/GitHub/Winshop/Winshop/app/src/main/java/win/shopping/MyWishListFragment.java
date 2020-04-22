package win.shopping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;



public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecycleView;
    public static WishListAdapter wishListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        wishlistRecycleView = view.findViewById(R.id.my_wishlist_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecycleView.setLayoutManager(linearLayoutManager);

        if(DBqueries.whislistModelList.size()==0){
            DBqueries.wishList.clear();
            DBqueries.loadWishlist(getContext(),true);
        }

        wishListAdapter = new WishListAdapter(DBqueries.whislistModelList,true);
        wishlistRecycleView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return view;
    }
}
