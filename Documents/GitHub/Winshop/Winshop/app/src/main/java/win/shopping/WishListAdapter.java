package win.shopping;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private boolean fromSearch;
    private List<WhislistModel> whislistModelList;
    private Boolean wishlist;

    public boolean isFromSearch() {
        return fromSearch;
    }

    public List<WhislistModel> getWhislistModelList() {
        return whislistModelList;
    }

    public void setWhislistModelList(List<WhislistModel> whislistModelList) {
        this.whislistModelList = whislistModelList;
    }

    public void setFromSearch(boolean fromSearch) {
        this.fromSearch = fromSearch;
    }

    public WishListAdapter(List<WhislistModel> whislistModelList, Boolean wishlist) {
        this.whislistModelList = whislistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = whislistModelList.get(position).getProductImage();
        String title = whislistModelList.get(position).getProductTitle();
        String price = whislistModelList.get(position).getCuttedPrice();
        String cuttedPrice = whislistModelList.get(position).getCuttedPrice();
        String rating = whislistModelList.get(position).getRating();
        String totalRatings = whislistModelList.get(position).getTotalRating();
        String productID = whislistModelList.get(position).getProductId();
        holder.setData(productID,resource,title,price,cuttedPrice,rating,totalRatings,position);
    }

    @Override
    public int getItemCount() {
        return whislistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView totalRating;
        private TextView rating;
        private View priceCut;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productPrice = itemView.findViewById(R.id.product_price);
            productTitle = itemView.findViewById(R.id.product_title);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            priceCut = itemView.findViewById(R.id.price_cut);
            rating = itemView.findViewById(R.id.WL_product_rating);
            totalRating = itemView.findViewById(R.id.total_rating);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(final String productId, String resource, String title, String price, String cutPrice, String averageRating, String totalRatingNo, final int index){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.home_icon)).into(productImage);
            productTitle.setText(title);
            rating.setText(averageRating);
            totalRating.setText("("+totalRatingNo+")" +"ratings");
            productPrice.setText("CAD."+ price);
            cuttedPrice.setText(cutPrice);

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else{
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteBtn.setEnabled(false);
                   DBqueries.removeDromWishlist(index,itemView.getContext());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(fromSearch){
                        ProductDetailActivity.fromSearch = true;
                    }
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });

        }
    }
}
