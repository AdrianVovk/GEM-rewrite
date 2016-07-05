package substance.mobile.gem.ui.activity

import android.content.Context
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.KeyEvent
import android.view.MenuItem
import substance.mobile.gem.R
import substance.mobile.gem.ui.custom.GEMActivity
import substance.mobile.gem.util.NowPlayingUtil
import substance.mobile.gem.util.bindView

class LibraryActivity : GEMActivity(), NavigationView.OnNavigationItemSelectedListener {

    var appDrawer: DrawerLayout by bindView(R.id.drawer_layout)
    var navigationView: NavigationView by  bindView(R.id.design_navigation_view)

    override fun getLayout() = R.layout.activity_library

    override fun setup() {
        val toggle = ActionBarDrawerToggle(
                this, appDrawer, toolbar, R.string.accessibility_open_drawer, R.string.accessibility_close_drawer)
        appDrawer.addDrawerListener(toggle)
        toggle.syncState()


        navigationView?.setNavigationItemSelectedListener(this)

        NowPlayingUtil.doInitialConfig(findViewById(R.id.constraintLayout))
    }

    private fun setupNavigation() {

    }

    override fun getMenu() = R.menu.library

    override fun onBackPressed() {
        if (appDrawer.isDrawerOpen(GravityCompat.START)) {
            appDrawer.closeDrawer(GravityCompat.START)
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