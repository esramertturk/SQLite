package com.example.esrapc.sqlliteornek;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView tvID, tvName, tvPhone,tvMail;
    ImageButton call,sms,mail;
    String strName, strPhone, strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvID = findViewById(R.id.tvID);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvMail = findViewById(R.id.tvMail);

        call = findViewById(R.id.callImgBtn);
        sms = findViewById(R.id.smsImgBtn);
        mail = findViewById(R.id.mailImgBtn);

        Intent intent = getIntent();

        tvID.setText(intent.getStringExtra("ID"));


        strName = intent.getStringExtra("ISIM");
        strEmail = intent.getStringExtra("EMAIL");
        strPhone = intent.getStringExtra("TEL_NO");

        tvName.setText(strName);
        tvMail.setText(strEmail);
        tvPhone.setText(strPhone);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: "+strPhone));
                startActivity(callIntent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms: "+strPhone));
                startActivity(smsIntent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("plain/text");
                String mailList[] = {strEmail};
                mailIntent.putExtra(Intent.EXTRA_EMAIL,mailList);
                startActivity(mailIntent);
            }
        });
    }
}

