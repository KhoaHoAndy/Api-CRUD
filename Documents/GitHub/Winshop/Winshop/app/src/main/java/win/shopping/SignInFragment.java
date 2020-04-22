package win.shopping;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView noAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText password;
    private ImageButton close_btn;
    private Button signIn;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private TextView forgotPassword;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        noAccount=view.findViewById(R.id.txt_sign_up);
        parentFrameLayout=getActivity().findViewById(R.id.register_fragLayout);
        email= view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        close_btn = view.findViewById(R.id.sign_in_close_btn);
        signIn = view.findViewById(R.id.sign_in_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        forgotPassword = view.findViewById(R.id.sign_in_forgot_pass);
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ResetPasswordFragment());
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });



        email.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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

        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                chekEmailandPassword();
            }
        });

    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slight_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    private void checkInput() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signIn.setEnabled(true);
                signIn.setTextColor(Color.rgb(255,255,255));
            }else{
                signIn.setEnabled(false);
                signIn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            signIn.setEnabled(false);
            signIn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void chekEmailandPassword() {
        if(email.getText().toString().matches(emailPattern)){
            if(password.length() >=8){
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mainIntent();
                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(getActivity(), "Incorrect password or email!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getActivity(), "Incorrect password or email!", Toast.LENGTH_LONG).show();
        }
    }

    private void mainIntent (){
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }


}
