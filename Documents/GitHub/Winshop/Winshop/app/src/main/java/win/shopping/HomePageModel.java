package win.shopping;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER = 0;
    public static final int HORIZONTAL_PRODUCT_VIEW = 1;
    public static final int GRID_PRODUCT_VIEW = 2;
    private int type;
    private String backgroundColor;
    ////////Banner Slider
    private List<SliderModel> sliderModelList;
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ////////Banner Slider

    //////Horizontal product view & grid product view
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    private List<WhislistModel> viewAllProductsList;

    public HomePageModel(int type,String title,String backgroundColor
            , List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<WhislistModel>viewAllProducts) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductsList = viewAllProducts;
    }

    public HomePageModel(int type,String title,String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public List<WhislistModel> getViewAllProductsList() {
        return viewAllProductsList;
    }

    public void setViewAllProductsList(List<WhislistModel> viewAllProductsList) {
        this.viewAllProductsList = viewAllProductsList;
    }

    //////Horizontal product view & grid product view
}
