package com.example.service

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.service.R.id.register
import com.google.firebase.auth.FirebaseAuth

class Login : ComponentActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var button: Button
    private lateinit var auth: FirebaseAuth
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
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance()
        editTextUsername=findViewById(R.id.email1)
        editTextPassword=findViewById(R.id.password1)
        button=findViewById(R.id.button1)

        progressBar=findViewById(R.id.progressbar)
        val btn: Button = findViewById(register)

        // Set a click listener on the button
        btn.setOnClickListener {
            // Create an Intent to start the Login activity
            val intent = Intent(this, RegisterActivity::class.java)

            // Start the LoginActivity
            startActivity(intent)
        }
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                progressBar.visibility = View.VISIBLE
                val email: String=editTextUsername.text.toString()
                val password: String= editTextPassword.text.toString()

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this@Login,"Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(this@Login,"Enter password", Toast.LENGTH_SHORT).show()
                    return
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                               Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, HomeActivity::class.java)

                                // Start the LoginActivity
                                startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.visibility = View.GONE
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@Login,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }
        })
    }
}