package robin.com.testtouchevent.utils

import android.view.MotionEvent

/**
 * Created by Robin Yeung on 10/18/18.
 */
fun parseAction(event: MotionEvent): Int {
    return event.action and MotionEvent.ACTION_MASK
}