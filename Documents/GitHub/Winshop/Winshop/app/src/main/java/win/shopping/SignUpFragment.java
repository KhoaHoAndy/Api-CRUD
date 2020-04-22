package win.shopping;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView haveAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton close;
    private Button signUpbtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        haveAccount=view.findViewById(R.id.txt_sign_in);
        parentFrameLayout=getActivity().findViewById(R.id.register_fragLayout);
        email=view.findViewById(R.id.sign_up_email);
        fullName=view.findViewById(R.id.sign_up_fullName);
        password=view.findViewById(R.id.sign_up_password);
        confirmPassword=view.findViewById(R.id.sign_up_confirm_password);

        close=view.findViewById(R.id.sign_up_close_btn);
        signUpbtn=view.findViewById(R.id.sign_up_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        haveAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setFragment(new SignInFragment());
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
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
        fullName.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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



        signUpbtn.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v){

                 checkEmailandPassword();
             }
        });
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    private void checkInput(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty((fullName.getText()))){
                if(!TextUtils.isEmpty(password.getText()) && password.length() >= 8){
                    if(!TextUtils.isEmpty(confirmPassword.getText())){
                        signUpbtn.setEnabled(true);
                        signUpbtn.setTextColor(Color.rgb(255,255,255));
                    }else{
                        signUpbtn.setEnabled(false);
                        signUpbtn.setTextColor(Color.argb(50,255,255,255));
                    }

                }else{
                    signUpbtn.setEnabled(false);
                    signUpbtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signUpbtn.setEnabled(false);
                signUpbtn.setTextColor(Color.argb(50,255,255,255));

            }
        }else{
            signUpbtn.setEnabled(false);
            signUpbtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void checkEmailandPassword(){

        Drawable customErrorIcon=getResources().getDrawable(R.mipmap.error_cross);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicHeight(),customErrorIcon.getIntrinsicWidth());



        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){

                signUpbtn.setEnabled(false);
                signUpbtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if(task.isSuccessful()){

                                    Map<Object,String> userdata=new HashMap<>();
                                    userdata.put("fullname",fullName.getText().toString());
                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                       mainIntent();
                                                    }else{
                                                        signUpbtn.setEnabled(true);
                                                        signUpbtn.setTextColor(Color.rgb(255,255,255));
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                                    }


                                                }
                                            });

                                    Intent mainIntent=new Intent(getActivity(),MainActivity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();

                                }else{
                                    signUpbtn.setEnabled(true);
                                    signUpbtn.setTextColor(Color.rgb(255,255,255));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }else{

                confirmPassword.setError("Password doesn't matched!",customErrorIcon);
            }
        }else{

            email.setError("Invalid Email!",customErrorIcon);
        }

    }

    private void mainIntent (){
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }

}
