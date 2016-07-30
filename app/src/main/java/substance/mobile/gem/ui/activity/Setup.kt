package substance.mobile.gem.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import substance.mobile.gem.R

class Setup : AppIntro2() {

    enum class Mode() {
        ALL,
        SIGNIN,
        PERMISSIONS
    }

    companion object {
        fun createIntent(context: Context, mode: Mode = Mode.ALL): Intent {
            val intent = Intent(context, Setup::class.java)
            intent.putExtra("mode", mode.ordinal)
            return intent
        }
        fun launch(context: Context, mode: Mode = Mode.ALL) {
            context.startActivity(createIntent(context, mode))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var signinOnly = false
        var permissionsOnly = false
        when (Mode.values()[intent.getIntExtra("mode", Mode.ALL.ordinal)]) {
            Mode.SIGNIN -> signinOnly = true
            Mode.PERMISSIONS -> permissionsOnly = true
            Mode.ALL -> {
                signinOnly = false
                permissionsOnly = false
            }
        }

        //Customise

        if (!(signinOnly || permissionsOnly)) {
            //Normal
        }

        if (!signinOnly) {

            if (permissionsOnly) return
        }

        //Sign in (no if statement because this wouldn't run if its permissions only.)
        addSlide(AppIntro2Fragment.newInstance("Sign in", "Sign into Gem Player", R.mipmap.ic_launcher, Color.GREEN))

    }


}
