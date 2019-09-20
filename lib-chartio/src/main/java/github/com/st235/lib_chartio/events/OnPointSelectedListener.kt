package github.com.st235.lib_chartio.events

import android.view.MotionEvent
import android.view.View
import github.com.st235.lib_chartio.utils.ObservableModel

class OnPointSelectedListener: ObservableModel<Pair<Float, Float>>(), View.OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                notifyWith(Pair(event.x, event.y))
            }
        }
        v.parent?.requestDisallowInterceptTouchEvent(true)
        return false
    }
}
