package win.shopping;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImage();
        String title = horizontalProductScrollModelList.get(position).getProductTitle();
        String description = horizontalProductScrollModelList.get(position).getProductDescription();
        String detail = horizontalProductScrollModelList.get(position).getProductLink();
        String productId = horizontalProductScrollModelList.get(position).getProductID();

        holder.setData(productId,resource,title,description,detail);
    }

    @Override
    public int getItemCount() {
        if(horizontalProductScrollModelList.size() >=8){
            return 8;
        }else{
            return horizontalProductScrollModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productDetail;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);
            productDescription = itemView.findViewById(R.id.h_s_product_description);
            productDetail = itemView.findViewById(R.id.h_s_product_detail);

        }

      private void setData(final String ID, String resource,String title,String description,String detail){
          Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.home_icon)).into(productImage);
          productTitle.setText(title);
          productDescription.setText("CAD."+description);
          productDetail.setText(detail);

          if(!title.equals("")) {
              itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                      productDetailIntent.putExtra("PRODUCT_ID",ID);
                      itemView.getContext().startActivity(productDetailIntent);
                  }
              });
          }
        }
    }
}
