package win.shopping;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProductDetailAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailAdapter(FragmentManager fm,int totalTab, String productDescription, String productOtherDetails
            , List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTab;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productDescription;
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList =productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body = productOtherDetails;
                return productDescriptionFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
