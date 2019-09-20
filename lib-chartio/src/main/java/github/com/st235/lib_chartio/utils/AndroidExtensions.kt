package github.com.st235.lib_chartio.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

internal inline fun Context.openTypedArray(
    set: AttributeSet?,
    attrs: IntArray,
    styleAttr: Int,
    styleRes: Int = 0,
    closure: (typedArray: TypedArray) -> Unit
) {
    val typedArray = obtainStyledAttributes(set, attrs, styleAttr, styleRes)
    closure(typedArray)
    typedArray.recycle()
}
