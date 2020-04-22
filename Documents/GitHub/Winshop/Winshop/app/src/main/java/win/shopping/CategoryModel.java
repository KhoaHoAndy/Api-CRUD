package win.shopping;

public class CategoryModel {
    private String CategoryIconLink;
    private String categoryName;

    public CategoryModel(String categoryIconLink, String categoryName) {
        CategoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }

    public String getCategoryIconLink() {
        return CategoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
