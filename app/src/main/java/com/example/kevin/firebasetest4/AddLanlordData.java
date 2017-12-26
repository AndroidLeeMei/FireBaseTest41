package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kevin.firebasetest4.FireBase.LanlordData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLanlordData extends AppCompatActivity {

    EditText editName,editPassword,editEmail,editPhone,editBank,editBankNumber,edtAccunt,editPasswordCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanlord_data_edit);
        findViews();


    }

    private void findViews(){
        EditText editName=(EditText)findViewById(R.id.edtName);
        EditText editPassword=(EditText)findViewById(R.id.edtPassWord1);
        EditText editEmail=(EditText)findViewById(R.id.edtEmail);
        EditText editPhone=(EditText)findViewById(R.id.edtPhone);
        EditText editBank=(EditText)findViewById(R.id.edtBank);
        EditText editBankNumber=(EditText)findViewById(R.id.edtBankNumber);
        EditText edtAccunt=(EditText)findViewById(R.id.edtAccount);
        EditText editPasswordCheck=(EditText)findViewById(R.id.edtPassWord);
    }

    public void btnSubmit(View target){
//        LanlordData lanlordData=new LanlordData(
//                editName.getText().toString()
//                ,editPassword.getText().toString()
//                ,editEmail.getText().toString()
//                ,editPhone.getText().toString()
//                ,editBank.getText().toString()
//                ,editBankNumber.getText().toString()
//                ,edtAccunt.getText().toString()
//                ,editPasswordCheck.getText().toString());
//
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Lanlord");
//        mDatabase.push().setValue(lanlordData);
    }

    public void btnCancel(View target){
        finish();
    }
}