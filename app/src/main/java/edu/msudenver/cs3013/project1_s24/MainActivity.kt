package edu.msudenver.cs3013.project1_s24

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    var isLoggedIn = false
    var loggedInUser = ""

    private val header: TextView
        get() = findViewById(R.id.header)

    private val backButton: Button
        get() = findViewById(R.id.back_button)

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize toggle
        toggle = ActionBarDrawerToggle(this,drawerLayout)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.Profile -> Toast.makeText(applicationContext,
                    "Clicked Profile",Toast.LENGTH_SHORT).show()
                R.id.Game -> Toast.makeText(applicationContext,
                    "Clicked Game",Toast.LENGTH_SHORT).show()
            }
            true
        }

        //Get the intent which started this activity
        intent.let { loginIntent ->

            val userNameForm = loginIntent?.getStringExtra(USER_NAME_KEY) ?: ""
            val passwordForm = loginIntent?.getStringExtra(PASSWORD_KEY) ?: ""

            val loggedInCorrectly = hasEnteredCorrectCredentials(userNameForm.trim(), passwordForm.trim())

            if (loggedInCorrectly) {
                setLoggedIn(userNameForm)
                isLoggedIn = true
            } else {
                header.text = getString(R.string.login_error)
                backButton.isVisible = true
                backButton.setOnClickListener {
                    //Finishes this activity and so goes back to the previous activity
                    finish()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_LOGGED_IN, isLoggedIn)
        outState.putString(LOGGED_IN_USERNAME, loggedInUser)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isLoggedIn = savedInstanceState.getBoolean(IS_LOGGED_IN, false)
        loggedInUser = savedInstanceState.getString(LOGGED_IN_USERNAME, "")

        if (isLoggedIn && loggedInUser.isNotBlank()) {
            setLoggedIn(loggedInUser)
        }
    }

    private fun setLoggedIn(userName: String) {
        loggedInUser = userName
        val welcomeMessage = getString(R.string.welcome_text, userName)
        backButton.isVisible = false
        header.text = welcomeMessage
    }

    private fun hasEnteredCorrectCredentials(
        userNameForm: String,
        passwordForm: String
    ): Boolean {
        return userNameForm.contentEquals(USER_NAME_CORRECT_VALUE) && passwordForm.contentEquals(
            PASSWORD_CORRECT_VALUE
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}