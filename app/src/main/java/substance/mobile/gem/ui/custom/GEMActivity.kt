package substance.mobile.gem.ui.custom

import android.content.Intent
import android.os.Bundle
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Transition
import android.util.Log
import android.util.TypedValue
import android.view.*
import substance.mobile.gem.R

abstract class GEMActivity : AppCompatActivity() {

    open val TAG = this.javaClass.simpleName

    ///////////////////////////////////////////////////////////////////////////
    // Layout
    ///////////////////////////////////////////////////////////////////////////

    abstract fun getLayout(): Int

    open fun inflate() {
    }

    open fun disableAutoInflate() = false

    ///////////////////////////////////////////////////////////////////////////
    // Variables and setup
    ///////////////////////////////////////////////////////////////////////////

    var toolbar: Toolbar? = null

    private fun setInternalVariables() {
        try {
            toolbar = findViewById(R.id.toolbar) as Toolbar
        } catch (e: Exception) {
            Log.e(TAG, "This activity doesn't have a toolbar or doesn't properly label it")
        }
    }

    open fun setVariables() {
    }

    abstract fun setup()

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sequence()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        sequence()
    }

    open fun sequence() {
        if (disableAutoInflate()) inflate() else setContentView(getLayout())
        setInternalVariables()
        setSupportActionBar(toolbar)
        setVariables()
        setup()
        setupTheme()
        if (useTransitions()) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS and Window.FEATURE_ACTIVITY_TRANSITIONS)
            setupTransitions()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
    ///////////////////////////////////////////////////////////////////////////
    // Transition
    ///////////////////////////////////////////////////////////////////////////

    fun useTransitions() = false

    open fun setupTransitions() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Menu
    ///////////////////////////////////////////////////////////////////////////

    open fun getMenu() = 0

    open fun onUpSelected() = false

    open fun useCustomUpBehaviour() = false

    open fun handleItemSelection(id: Int?) = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = when (getMenu()) {
        0 -> false
        else -> {
            menuInflater.inflate(getMenu(), menu)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> if (useCustomUpBehaviour()) onUpSelected() else handleItemSelection(item?.itemId)
        R.id.action_settings -> openSettings()
        else -> handleItemSelection(item?.itemId)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Theme
    ///////////////////////////////////////////////////////////////////////////

    fun setupTheme() {
    }

    @ColorInt fun resolveColorAttr(@AttrRes resId: Int): Int {
        val value: TypedValue = TypedValue()
        getTheme().resolveAttribute(resId, value, true)
        return value.data
    }

    ///////////////////////////////////////////////////////////////////////////
    // Other
    ///////////////////////////////////////////////////////////////////////////

    fun openSettings(): Boolean {
        //TODO
        return true
    }

    fun getRoot() = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
}