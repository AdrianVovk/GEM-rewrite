package substance.mobile.gem

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.AppLaunchChecker
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import substance.mobile.gem.ui.activity.Setup

/**
 * Created by adria on 29/07/2016.
 */
class GEMApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //if (!AppLaunchChecker.hasStartedFromLauncher(this)) {
//            Setup.launch(this)
//            return
//        }
        if (FirebaseAuth.getInstance().currentUser == null) Setup.launch(this, Setup.Mode.SIGNIN) //If we are not signed in, sign in
        else Log.d("APP", FirebaseAuth.getInstance().currentUser?.uid ?: "Lolwut")
        if (!checkPermissions()) Setup.launch(this, Setup.Mode.PERMISSIONS)
    }

    fun checkPermissions() = true //TODO

}