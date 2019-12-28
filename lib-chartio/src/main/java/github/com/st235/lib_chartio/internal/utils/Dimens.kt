package github.com.st235.lib_chartio.internal.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Dimension
import androidx.annotation.Px


/**
 * Converts values to its real pixel size
 * using system density factor
 *
 * @return value in pixels
 */
@Px
internal fun Int.toPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics).toInt()
}

/**
 * Converts values to its real pixel size
 * using system density factor
 *
 * @return value in pixels
 */
@Dimension(unit = Dimension.PX)
internal fun Float.toPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        Resources.getSystem().displayMetrics)
}

/**
 * Converts values to its real pixel size
 * using system scaled density factor
 *
 * @return value in pixels
 */
@Dimension(unit = Dimension.PX)
internal fun Float.spToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this,
        Resources.getSystem().displayMetrics)
}
