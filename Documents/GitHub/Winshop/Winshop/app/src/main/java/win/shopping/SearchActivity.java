package win.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TextView textView;
    private SearchView searchView;
    private RecyclerView search_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_recyclerView = findViewById(R.id.search_recycle_view);
        searchView = findViewById(R.id.search_view);
        textView = findViewById(R.id.text_view);

        search_recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        search_recyclerView.setLayoutManager(layoutManager);

        final List<WhislistModel>list = new ArrayList<>();
        final List<String> ids = new ArrayList<>();

        final Adapter adapter = new Adapter(list,false);
        adapter.setFromSearch(true);
        search_recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                list.clear();
                ids.clear();

                final String [] tags = s.toLowerCase().split(" ");
                for(final String tag:tags){
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags",tag)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                    WhislistModel model = new WhislistModel(documentSnapshot.getId()
                                            ,documentSnapshot.get("product_image_1").toString()
                                            , documentSnapshot.get("product_title").toString()
                                            , documentSnapshot.get("product_price" ).toString()
                                            , (String) documentSnapshot.get("average_rating")
                                            , (String) documentSnapshot.get("total_rating")
                                            , (String) documentSnapshot.get("product_full_title"));

                                    model.setTags((ArrayList<String>) documentSnapshot.get("tags"));
                                    if(!ids.contains(model.getProductId())){
                                        list.add(model);
                                        ids.add(model.getProductId());
                                    }
                                }
                                if(tag.equals(tags[tags.length-1])){
                                    if(list.size()==0){
                                        textView.setVisibility(View.VISIBLE);
                                        search_recyclerView.setVisibility(View.GONE);

                                    }else{
                                        textView.setVisibility(View.GONE);
                                        search_recyclerView.setVisibility(View.VISIBLE);
                                        adapter.getFilter().filter(s);
                                    }

                                }
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(SearchActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    class Adapter extends WishListAdapter implements Filterable{
        private List<WhislistModel> orginalList;

        public Adapter(List<WhislistModel> whislistModelList, Boolean wishlist) {
            super(whislistModelList, wishlist);
            orginalList =whislistModelList;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();

                    List<WhislistModel> filteredList = new ArrayList<>();

                    final String [] tags = charSequence.toString().toLowerCase().split(" ");

                    for(WhislistModel model : orginalList){
                        ArrayList<String> presentTags = new ArrayList<>();
                        for(String tag:tags){
                            if(model.getTags().contains(tag)){
                                presentTags.add(tag);

                            }
                        }
                        model.setTags(presentTags);
                    }
                    for(int i =tags.length ;i > 0;i--){
                        for(WhislistModel model:orginalList){
                            if(model.getTags().size()==1){
                                filteredList.add(model);
                            }
                        }
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if(filterResults.count>0){
                        setWhislistModelList((List<WhislistModel>)filterResults.values);
                    }


                    notifyDataSetChanged();
                }
            };
        }
    }
}
