package win.shopping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecycleView;
    public List<ProductSpecificationModel> productSpecificationModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecycleView = view.findViewById(R.id.product_specification_recycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecificationRecycleView.setLayoutManager(linearLayoutManager);

//        = new ArrayList<>();
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecycleView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}
