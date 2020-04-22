package win.shopping;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment() {
        // Required empty public constructor
    }
    private EditText resetPassword_Email;
    private Button resetPass_btn;
    private TextView goBack;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        resetPassword_Email = view.findViewById(R.id.reset_pass_email);
        resetPass_btn = view.findViewById(R.id.reset_pass_btn);
        goBack = view.findViewById(R.id.tv_reset_pass_goback);
        parentFrameLayout = getActivity().findViewById(R.id.register_fragLayout);
        firebaseAuth = FirebaseAuth.getInstance();



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetPassword_Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        resetPass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPass_btn.setEnabled(false);
                resetPass_btn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(resetPassword_Email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Email sent successfully",Toast.LENGTH_LONG).show();
                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }
                                resetPass_btn.setEnabled(true);
                                resetPass_btn.setTextColor(Color.rgb(255,255,255));
                            }
                        });

            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });


    }

    private void checkInput() {
        if(TextUtils.isEmpty(resetPassword_Email.getText())){
            resetPass_btn.setEnabled(false);
            resetPass_btn.setTextColor(Color.argb(50,255,255,255));
        }else{
            resetPass_btn.setEnabled(true);
            resetPass_btn.setTextColor(Color.rgb(255,255,255));
        }

    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
