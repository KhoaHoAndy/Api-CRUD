package win.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<WhislistModel> whislistModelList;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycle_View_all);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code",-1);

        if(layout_code == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            WishListAdapter wishListAdapter = new WishListAdapter(whislistModelList,false);
            recyclerView.setAdapter(wishListAdapter);
            wishListAdapter.notifyDataSetChanged();

        }else if(layout_code == 1) {
            gridView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
