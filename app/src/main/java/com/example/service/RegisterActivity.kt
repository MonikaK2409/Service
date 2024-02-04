package com.example.service

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.service.R.id.email
import com.example.service.R.id.password
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener


class RegisterActivity : ComponentActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var spinner: Spinner
    private lateinit var button: Button
    private lateinit var auth:FirebaseAuth
    private lateinit var progressBar: ProgressBar
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
        editTextUsername=findViewById(email)
        editTextPassword=findViewById(password)
        button=findViewById(R.id.next)
        spinner = findViewById(R.id.dropdown_spinner)
        progressBar=findViewById(R.id.progressbar)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                progressBar.visibility = View.VISIBLE
                val email: String=editTextUsername.text.toString()
                val password: String= editTextPassword.text.toString()
                val service: String= spinner.toString()
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this@RegisterActivity,"Enter Email",Toast.LENGTH_SHORT).show()
                    return
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(this@RegisterActivity,"Enter password",Toast.LENGTH_SHORT).show()
                    return
                }
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->

                        if (task.isSuccessful) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity ,
                                "Account Created",
                                Toast.LENGTH_SHORT,
                            ).show()
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
        })

    }
}



