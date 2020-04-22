package win.shopping;

import java.net.URL;

public class HorizontalProductScrollModel {

    private String productID;
    private String productImage;
    private String productTitle;
    private String productDescription;
    private String productLink;

    public HorizontalProductScrollModel(String productID, String productImage, String productTitle, String productDescription, String productLink) {
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productLink = productLink;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }
}
