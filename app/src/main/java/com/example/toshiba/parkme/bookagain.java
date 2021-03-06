package com.example.toshiba.parkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bookagain extends AppCompatActivity {

    FirebaseUser currentuser;
    DatabaseReference databaseReference,databaseReference1;
    int fro,tt;

    TextView fromView;
    EditText hours ;
    int hrs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybookagain);

        hours = (EditText) findViewById(R.id.editTo1);
        fromView = (TextView) findViewById(R.id.fromtime1);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             fro = dataSnapshot.child("Users").child(currentuser.getUid()).child("from").getValue(Integer.class);
             tt= dataSnapshot.child("Users").child(currentuser.getUid()).child("to").getValue(Integer.class);
             fromView.setText(Integer.toString(fro));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button book = (Button) findViewById(R.id.check1);

        databaseReference1 = FirebaseDatabase.getInstance().getReference();




        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String h = hours.getText().toString();
                hrs = Integer.parseInt(h);
                if((hrs + tt)<=24) {
                    databaseReference1.child("Users").child(currentuser.getUid()).child("to").setValue(hrs + tt);

                    Toast.makeText(getApplicationContext(), "Booking extended successfully!", Toast.LENGTH_LONG).show();
                    Intent backIntent = new Intent(bookagain.this, ProfileActivity.class);
                    startActivity(backIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "Booking for next day not available", Toast.LENGTH_LONG).show();
                }


            }
        });

        Button back_button = (Button) findViewById(R.id.backbook1);

        back_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(bookagain.this, ProfileActivity.class);
                startActivity(backIntent);
            }
        }));




    }
}
