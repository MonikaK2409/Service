package com.example.service

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.service.R.id.dropdown_spinner
import com.example.service.R.id.email
import com.example.service.R.id.password
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.auth.User


class RegisterActivity : ComponentActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var spinner: Spinner
    private lateinit var button: Button
    private lateinit var auth:FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var name: TextView
    private lateinit var database: DatabaseReference


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, HomeActivity::class.java)

            // Start the LoginActivity
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        auth= FirebaseAuth.getInstance()
        name=findViewById(R.id.name)
        editTextUsername=findViewById(email)
        editTextPassword=findViewById(password)
        button=findViewById(R.id.next)
        database = FirebaseDatabase.getInstance().reference
        spinner = findViewById(dropdown_spinner)
        progressBar=findViewById(R.id.progressbar)
        button.setOnClickListener(object : View.OnClickListener {


            override fun onClick(v: View?) {
                progressBar.visibility = View.VISIBLE
                val selectedSpinnerItem = spinner.selectedItem.toString()
                val userName: String = name.text.toString()
                val email: String=editTextUsername.text.toString()
                val password: String= editTextPassword.text.toString()

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(this@RegisterActivity,"Enter Name",Toast.LENGTH_SHORT).show()
                    return
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this@RegisterActivity,"Enter Email",Toast.LENGTH_SHORT).show()
                    return
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(this@RegisterActivity,"Enter password",Toast.LENGTH_SHORT).show()
                    return
                }


                // Set up the item selection listener




                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity ,
                                "Account Created",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val user = User(userName, email, selectedSpinnerItem)
                            writeNewUser(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.visibility = View.GONE
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@RegisterActivity ,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }
            fun writeNewUser(user: com.example.service.User) {


                Log.d("MyTag", "writeNewUser function called")
                val userRef = database.child("users").push()
                userRef.setValue(user)
            }
        })

    }


}



