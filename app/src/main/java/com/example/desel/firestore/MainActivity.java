package com.example.desel.firestore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    // Basic Declarations
    private EditText etMain;
    private Button btnSave;

    // Firestore Declarations
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Basic initiating
        etMain = findViewById(R.id.etMain);
        btnSave = findViewById(R.id.btnSave);

        // Firestore instance initiating
        mFirestore = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Getting text from edit text
                String username = etMain.getText().toString();

                // Getting hashmap
                Map<String, String> userMap = new HashMap<>();

                // Setting up the Columns and their accepted values
                userMap.put("name", username);
                userMap.put("image", "image_link");

                // Creating the Collection (Database-Table that everything will be
                // stored under.
                // In this case, the user creates a user DB if none exists, else they
                // write to it. When writing to it, FS will create an automatic UID,
                // and the userMap columns (Documents) will be made and stored.
                mFirestore.collection("users").add(userMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                        {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(MainActivity.this, "Success",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
