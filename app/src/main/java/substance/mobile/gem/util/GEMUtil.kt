package substance.mobile.gem.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

object GEMUtil {

    fun colourDrawable(context: Context, @DrawableRes drawable: Int, @ColorRes colour: Int): Drawable {
        return colourDrawable(ContextCompat.getDrawable(context, drawable), ContextCompat.getColor(context, colour))
    }

    fun colourDrawable(drawable: Drawable, @ColorInt color: Int): Drawable {
        val wrapped = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, color)
        return wrapped
    }

}
