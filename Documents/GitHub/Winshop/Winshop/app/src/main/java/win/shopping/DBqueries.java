package win.shopping;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public static List<CategoryModel> categoryModelList = new ArrayList<>();


    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadCategoryName = new ArrayList<>();
    public static List<String> wishList = new ArrayList<>();
    public static List<WhislistModel> whislistModelList = new ArrayList<>();
    public static List<String> myRateId = new ArrayList<>();
    public static List<Long> productRating = new ArrayList<>();


    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context){

        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString()
                                        ,documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static void loadFragmentData(final HomePageAdapter adapter, final Context context, final int index, String categoryNames){
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryNames.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if ((long)documentSnapshot.get("view_type")==0){
                                    List<SliderModel>sliderModelList = new ArrayList<>();
                                    long no_of_banner = (long)documentSnapshot.get("no_of_banner");
                                    for( long x = 1; x < no_of_banner+1; x++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_"+x).toString()
                                                ,documentSnapshot.get("banner_"+ x +"_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0,sliderModelList));
                                }else if((long)documentSnapshot.get("view_type")==1){
                                    List<WhislistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for( long x = 1; x < no_of_products+1; x++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()));

                                        viewAllProductList.add(new WhislistModel(documentSnapshot.get("product_ID_"+x).toString()
                                                ,documentSnapshot.get("product_image_"+x).toString()
                                        , documentSnapshot.get("product_title_" + x).toString()
                                        , documentSnapshot.get("product_price_" + x).toString()
                                        , (String)documentSnapshot.get("average_rating_"+ x)
                                        ,(String)documentSnapshot.get("total_rating")
                                        , (String)documentSnapshot.get("product_full_title_" + x)));
                                    }
                                    if(documentSnapshot !=null){
                                        lists.get(index).add(new HomePageModel(1,documentSnapshot.get("layout_title").toString()
                                            ,documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList,viewAllProductList));}
                                    else{

                                    }
                                }else if((long)documentSnapshot.get("view_type")==2){
                                    List<HorizontalProductScrollModel> gridProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for( long x = 1; x < no_of_products+1; x++) {
                                        gridProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_Id_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(2,documentSnapshot.get("layout_title").toString()
                                            ,documentSnapshot.get("layout_background").toString(),gridProductScrollModelList));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishlist ( final Context context, final boolean loadProductData){
        wishList.clear();
        firebaseFirestore.collection("PRODUCTS").document("MY_WISH_LIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(long x = 0; x < (long)task.getResult().get("list_size"); x++){
                        wishList.add(task.getResult().get("product_ID_"+x).toString());
                        if(loadProductData) {
                            whislistModelList.clear();
                            firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        whislistModelList.add(new WhislistModel(task.getResult().get("product_ID").toString()
                                                ,task.getResult().get("product_image_1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , task.getResult().get("product_price" ).toString()
                                                , (String) task.getResult().get("average_rating")
                                                , (String) task.getResult().get("total_rating")
                                                , (String) task.getResult().get("product_full_title")));

                                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }

                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void removeDromWishlist(final int index, final Context context){
        wishList.remove(index);
        Map<String, Object> updateList = new HashMap<>();
        for(int x =0; x <wishList.size(); x++){
            updateList.put("product_ID_"+x, wishList.get(x));
        }
        updateList.put("list_size",(long)wishList.size());
        firebaseFirestore.collection("PRODUCTS").document("MY_WISH_LIST")
                .set(updateList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(whislistModelList.size() !=0){
                        whislistModelList.remove(index);
                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                    }
                    ProductDetailActivity.ALREADY_ADDED_TO_FAVORITE=false;
                    Toast.makeText(context,"Removed successfully!",Toast.LENGTH_SHORT).show();
                }else{
                    ProductDetailActivity.addToFavoriteBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
                ProductDetailActivity.addToFavoriteBtn.setEnabled(true);
            }
        });
    }

    public static void loadRatingList(){
        
    }

    public static void clearData (){
        categoryModelList.clear();
        lists.clear();
        loadCategoryName.clear();
        wishList.clear();
        whislistModelList.clear();
    }
}
