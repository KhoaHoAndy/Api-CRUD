package win.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static win.shopping.ProductSpecificationFragment.*;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewpagerIndicator;
    public static boolean ALREADY_ADDED_TO_FAVORITE = false;
    public static FloatingActionButton addToFavoriteBtn;
    private ViewPager productDetailViewpager;
    private TabLayout productDetailsTabLayout;
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailTabsContainer;
    private TextView productTitle;
    private TextView productPrice;
    private TextView cuttedPrice;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productOnlyDescriptionBody;

    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    //////rating layout
    private LinearLayout rateNowContainer;
    private TextView totalRating;
    private LinearLayout ratingNoContainer;
    private TextView totalRatingFigure;
    private LinearLayout ratingProgressBarContainer;
    private TextView averageRating;
    //////rating layout
    private String productID;
    private DocumentSnapshot documentSnapshot;
    public static boolean fromSearch;

    FirebaseFirestore firebaseFirestore;
    private Button check_product_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        check_product_btn = findViewById(R.id.btn_check_product);
        productImageViewPager = findViewById(R.id.product_image_viewPage);
        viewpagerIndicator = findViewById(R.id.viewpager_indicater);
        addToFavoriteBtn = findViewById(R.id.add_to_favorite_btn);
        productDetailViewpager= findViewById(R.id.product_detail_viewpage);
        productDetailsTabLayout = findViewById(R.id.product_detail_tablayout);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);

        productDetailsOnlyContainer = findViewById(R.id.product_detail_container);
        productDetailTabsContainer = findViewById(R.id.product_detail_tab_container);
        productOnlyDescriptionBody = findViewById(R.id.product_detail_body);

        totalRating = findViewById(R.id.total_ratings);
        ratingNoContainer = findViewById(R.id.ratinmgs_number_container);
        totalRatingFigure = findViewById(R.id.total_rating_figure);
        ratingProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.average_rating);

        final List<String> productImages = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        productID = getIntent().getStringExtra("PRODUCT_ID");
         firebaseFirestore.collection("PRODUCTS").document(getIntent().getStringExtra("PRODUCT_ID"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    documentSnapshot = task.getResult();

                    for (long x = 1; x <(long)documentSnapshot.get("no_of_product_images")+1; x++){
                        productImages.add(documentSnapshot.get("product_image_"+x).toString());
                    }
                    ProductImageAdapter productImageAdapter =new ProductImageAdapter(productImages);
                    productImageViewPager.setAdapter(productImageAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+")ratings");
                    productPrice.setText("CAD."+documentSnapshot.get("product_price").toString()+"/-)");
                    cuttedPrice.setText("CAD."+documentSnapshot.get("product_cutted_price").toString());

                    if((boolean)documentSnapshot.get("use_tab_layout")){
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDetailTabsContainer.setVisibility(View.VISIBLE);
                        productDescription = documentSnapshot.get("product_description").toString();

                        productOtherDetails = documentSnapshot.get("product_other_details ").toString();

                        for(long x = 1; x < (long)documentSnapshot.get("total_spec_titles")+1; x++){
                            productSpecificationModelList.add(
                                    new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString())
                            );
                            for(long i=1; i < (long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1; i++){
                               productSpecificationModelList.add(
                                        new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_"+i+"_name").toString()
                                                ,documentSnapshot.get("spec_title_"+x+"_field_"+i+"_value").toString())
                                );
                            }
                        }

                    }else{
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productDetailTabsContainer.setVisibility(View.GONE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_other_details ").toString());
                    }

                    totalRating.setText((long)documentSnapshot.get("total_ratings")+"ratings");
                    for(int x = 0; x < 5; x++){
                        TextView rating = (TextView)ratingNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                        ProgressBar progressBar = (ProgressBar)ratingProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                    }
                    totalRatingFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailViewpager.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));

                    if(DBqueries.wishList.size() ==0){
                        DBqueries.loadWishlist(ProductDetailActivity.this,false);
                    }

                    if(DBqueries.wishList.contains(productID)){
                        ALREADY_ADDED_TO_FAVORITE = true;
                    }else{
                        ALREADY_ADDED_TO_FAVORITE = false;
                    }
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewpagerIndicator.setupWithViewPager(productImageViewPager,true);

        addToFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavoriteBtn.setEnabled(false);
                if(ALREADY_ADDED_TO_FAVORITE){
                    int index = DBqueries.wishList.indexOf(productID);
                    DBqueries.removeDromWishlist(index,ProductDetailActivity.this);
                    addToFavoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                }else{
                    addToFavoriteBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    Map<String,Object> addProduct = new HashMap<>();
                    addProduct.put("product_ID_"+String.valueOf(DBqueries.wishList.size()),productID);

                    firebaseFirestore.collection("PRODUCTS").document("MY_WISH_LIST")
                            .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Map<String,Object> updateListSize = new HashMap<>();
                                updateListSize.put("list_size",DBqueries.wishList.size()+1);
                                firebaseFirestore.collection("PRODUCTS").document("MY_WISH_LIST")
                                        .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(DBqueries.whislistModelList.size() !=0){
                                                DBqueries.whislistModelList.add(new WhislistModel(productID
                                                        ,documentSnapshot.get("product_image_1").toString()
                                                        , documentSnapshot.get("product_title" ).toString()
                                                        , documentSnapshot.get("product_price" ).toString()
                                                        , (String)documentSnapshot.get("average_rating")
                                                        ,(String)documentSnapshot.get("total_rating")
                                                        , (String)documentSnapshot.get("product_full_title")));
                                            }
                                            ALREADY_ADDED_TO_FAVORITE=true;
                                            addToFavoriteBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                            DBqueries.wishList.add(productID);
                                            Toast.makeText(ProductDetailActivity.this,"Added successfully to my favorite list",Toast.LENGTH_SHORT).show();
                                        }else{
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                                        }
                                        addToFavoriteBtn.setEnabled(true);
                                    }
                                });
                            }else{
                                addToFavoriteBtn.setEnabled(true);
                                String error = task.getException().getMessage();
                                Toast.makeText(ProductDetailActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


        productDetailViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productDetailViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ///////rating layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for(int i = 0;i < rateNowContainer.getChildCount();i++){
            final int starPosition = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(starPosition);
                }
            });
        }
        /////rating layout

        check_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.telus.com/en/nb/mobility/phones/samsung-galaxy-s20-ultra-5g";
                Intent link = new Intent(Intent.ACTION_VIEW);
                link.setData(Uri.parse(url));
                startActivity(link);
            }
        });
    }

    private void setRating(int startPosition){
        for(int i = 0;i < rateNowContainer.getChildCount();i++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(i <= startPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_favorite_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id== android.R.id.home){
            finish();
            return true;
        }else if(id==R.id.main_search_icon){
            if(fromSearch){
                finish();
            }else{
                Intent searchIntent = new Intent(this,SearchActivity.class);
                startActivity(searchIntent);
            }
            return  true;
        }else if(id==R.id.main_favorite_icon){
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromSearch=false;
    }
}
