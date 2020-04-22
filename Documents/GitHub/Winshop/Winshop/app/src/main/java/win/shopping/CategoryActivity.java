package win.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static win.shopping.DBqueries.lists;
import static win.shopping.DBqueries.loadCategoryName;
import static win.shopping.DBqueries.loadFragmentData;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecycleView;
    private  HomePageAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecycleView = findViewById(R.id.category_recycleView);
        ////banner slider
        ///banner slider
        ///horizontal product layout
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        ///horizontal product layout

        ////////////////////////////////
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(testingLayoutManager);

        int listPosition = 0;
        for(int x = 0; x < loadCategoryName.size(); x++){
            if(loadCategoryName.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
        }
        if(listPosition == 0){
            loadCategoryName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(loadCategoryName.size()-1));
            loadFragmentData(adapter,this,loadCategoryName.size()-1,title);
        }else{
            adapter = new HomePageAdapter(lists.get(listPosition));
        }


        categoryRecycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id== R.id.main_search_icon){
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
