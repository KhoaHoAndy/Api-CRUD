package win.shopping;

import java.util.ArrayList;
import java.util.List;

public class WhislistModel {

    private String productId;
    private String productImage;
    private String productTitle;
    private String rating;
    private String totalRating;
    private String productPrice;
    private String cuttedPrice;
    private ArrayList<String> tags;

    public WhislistModel(String productID,String productImage, String productTitle, String rating, String totalRating, String productPrice, String cuttedPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productId =productID;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }
}
