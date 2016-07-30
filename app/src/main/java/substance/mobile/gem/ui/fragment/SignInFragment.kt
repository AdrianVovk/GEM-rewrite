package substance.mobile.gem.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

import substance.mobile.gem.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SignInFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment() : Fragment() {

    val GOOG_REQ_CODE = 68169

    //Google Login Properties
    val googleOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
    }
    val googleApiClient by lazy {
        GoogleApiClient.Builder(activity).enableAutoManage(activity, {
            fail()
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleOptions).build()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        view?.setBackgroundColor(Color.WHITE) //TODO: Actual colour

        emailAuth.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString()).addOnCompleteListener {
                if (!it.isSuccessful) fail()
            }
        }

        googleAuth.setOnClickListener {
            startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), GOOG_REQ_CODE)
        }
    }

    private fun fail() {
        Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show() //TODO: Show some sort of fail screen
    }
}
