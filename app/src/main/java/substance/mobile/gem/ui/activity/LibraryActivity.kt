package substance.mobile.gem.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.URLUtil
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import substance.mobile.gem.R
import substance.mobile.gem.ui.custom.GEMActivity
import substance.mobile.gem.util.NowPlayingUtil
import kotlinx.android.synthetic.main.activity_library.*
import kotlinx.android.synthetic.main.nav_header_library.*
import substance.mobile.gem.GEMApp
import java.net.URL

class LibraryActivity : GEMActivity(), NavigationView.OnNavigationItemSelectedListener {

    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val authListener = FirebaseAuth.AuthStateListener {
        val usr = it.currentUser
        if (usr != null) {
            val name = usr.displayName
            val email = usr.email
            val uid = usr.uid

            signedInName?.text = name
            signedInEmail?.text = email
            Toast.makeText(this, "Hi ${name ?: email}!", Toast.LENGTH_SHORT).show()
        } else {
            signedInName?.text = "Signed Out"
        }
    }

    override fun getLayout() = R.layout.activity_library

    override fun setup() {
        val toggle = ActionBarDrawerToggle(
                this, drawerContainer, toolbar, R.string.accessibility_open_drawer, R.string.accessibility_close_drawer)
        drawerContainer.addDrawerListener(toggle)
        toggle.syncState()
        appDrawer.setNavigationItemSelectedListener(this)
        //NowPlayingUtil.doInitialConfig(findViewById(R.id.constraintLayout))
    }

    private fun signIn() {
        signIn.setOnClickListener { auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString()) }
        signUp.setOnClickListener {
            if (!pass.text.toString().equals(passVerify.text.toString())) {
                passVerify.error = "Passwords don't match"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
            if (!nameText.text.isBlank()) auth.currentUser?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(nameText.text.toString()).build())
        }
        signOut.setOnClickListener {
            auth.signOut()
            Auth.GoogleSignInApi.signOut(apiClient)
        }

        google_auth.setOnClickListener {
            startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(apiClient), 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { if (!it.isSuccessful) Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show(); }
            } else {
                //Fail
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }

    private fun setupNavigation() {

    }

    override fun getMenu() = R.menu.library

    override fun onBackPressed() {
        if (drawerContainer.isDrawerOpen(GravityCompat.START)) {
            drawerContainer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        //TODO
        else -> true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = NowPlayingUtil.handleVolumeSlider(findViewById(R.id.constraintLayout), keyCode)

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, LibraryActivity::class.java)
            return intent
        }
    }
}