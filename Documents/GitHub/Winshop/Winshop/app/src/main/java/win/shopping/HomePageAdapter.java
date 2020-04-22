package win.shopping;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 2:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_add_banner,parent,false);
                return new BannerSliderViewHolder(view);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductViewHolder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (homePageModelList.get(position).getType()){
                case HomePageModel.BANNER_SLIDER:
                    List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                    ((BannerSliderViewHolder)holder).setBannerSliderViewPage(sliderModelList);
                    break;
                case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                    String layoutColor = homePageModelList.get(position).getBackgroundColor();
                    String horizontalProductTitle = homePageModelList.get(position).getTitle();
                    List<WhislistModel> viewAllProductList = homePageModelList.get(position).getViewAllProductsList();
                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                    ((HorizontalProductViewHolder)holder).setHorizontalRecycleLayout(horizontalProductScrollModelList
                            ,horizontalProductTitle,layoutColor,viewAllProductList);
                    break;
                case HomePageModel.GRID_PRODUCT_VIEW:
                    String gridLayoutColor =homePageModelList.get(position).getBackgroundColor();
                    String gridProductTitle = homePageModelList.get(position).getTitle();
                    List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                    ((GridProductViewHolder)holder).setGridProductLayout(gridProductScrollModelList,gridProductTitle,gridLayoutColor);
                    break;
                default:
                    return;
            }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{

        private ViewPager bannerSliderViewPage;
        private int currentPage ;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPage = itemView.findViewById(R.id.banner_slider_view_page);

        }

        private void setBannerSliderViewPage(final List<SliderModel>sliderModelList){
            currentPage=2;
            if(timer!=null){
                timer.cancel();
            }

            arrangedList = new ArrayList<>();
            for(int x =0; x < sliderModelList.size(); x++){
                arrangedList.add(x,sliderModelList.get(x));
            }

            arrangedList.add(0,sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
            bannerSliderViewPage.setAdapter(sliderAdapter);
            bannerSliderViewPage.setClipToPadding(false);
            bannerSliderViewPage.setPageMargin(20);

            bannerSliderViewPage.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = 1;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state == ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(arrangedList);
                    }
                }
            };

            bannerSliderViewPage.addOnPageChangeListener(onPageChangeListener);

            startbannerSlideShow(arrangedList);

            bannerSliderViewPage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(arrangedList);
                    stopbannerSlideShow();
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        startbannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });
        }
        private void pageLooper(List<SliderModel>sliderModelList){
            if(currentPage == sliderModelList.size() - 2){
                currentPage = 2;
                bannerSliderViewPage.setCurrentItem(currentPage,false );
            }
            if(currentPage == 1){
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPage.setCurrentItem(currentPage,false);
            }

        }

        private void startbannerSlideShow(final List<SliderModel>sliderModelList){
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if(currentPage >= sliderModelList.size()){
                        currentPage =1;
                    }
                    bannerSliderViewPage.setCurrentItem(currentPage++,true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);

        }

        private void stopbannerSlideShow(){
            timer.cancel();
        }
    }
    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button viewAllBtn;
        private RecyclerView horizontalRecycleView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_layout_title);
            viewAllBtn = itemView.findViewById(R.id.horizontal_layout_btn);
            horizontalRecycleView = itemView.findViewById(R.id.horizontal_layout_recycle_view);
            horizontalRecycleView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalRecycleLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList
                , final String title, String color, final List<WhislistModel>viewAllProducts){

            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);

            if(horizontalProductScrollModelList.size() < 8){
                viewAllBtn.setVisibility(View.VISIBLE);
                viewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.whislistModelList = viewAllProducts;
                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }else{
                viewAllBtn.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecycleView.setLayoutManager(linearLayoutManager);
            horizontalRecycleView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }

    }
    public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;


        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.grid_container);
             gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
             gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_btn);
             gridProductLayout = itemView.findViewById(R.id.grid_layout);

        }
        private void setGridProductLayout (final List<HorizontalProductScrollModel>horizontalProductScrollModelList, final String title, String color){

            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for(int x = 0; x < 4; x ++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                final TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productDetails = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_detail);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage())
                        .apply(new RequestOptions().placeholder(R.mipmap.home_icon))
                        .into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDescription.setText("CAD."+horizontalProductScrollModelList.get(x).getProductDescription());
                productDetails.setText(horizontalProductScrollModelList.get(x).getProductLink());

                if(!title.equals("")) {
                    final int finalX = x;
                    gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                            productDetailsIntent.putExtra("PRODUCT_ID", horizontalProductScrollModelList.get(finalX).getProductID());
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });
                }
            }

            gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }
}
