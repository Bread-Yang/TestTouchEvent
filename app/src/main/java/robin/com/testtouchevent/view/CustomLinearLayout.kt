package robin.com.testtouchevent.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import robin.com.testtouchevent.detector.CustomGestureDetector
import robin.com.testtouchevent.detector.GestureDetectorListener
import robin.com.testtouchevent.utils.parseAction

/**
 * Created by Robin Yeung on 10/18/18.
 */
class CustomLinearLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {


    companion object {
        private val TAG = "CustomLinearLayout"
    }

    lateinit var gestureDetector : CustomGestureDetector

    init {

        val listener = object : GestureDetectorListener {
            override fun onFingerDown(downX: Float, downY: Float) {
                Log.e(TAG, "onFingerDown()")
            }

            override fun onFingerUp(upX: Float, upY: Float) {
                Log.e(TAG, "onFingerUp()")
            }

            override fun onFingerCancel() {
                Log.e(TAG, "onFingerCancel()")
            }

            override fun onDrag(dx: Float, dy: Float, x: Float, y: Float, rootLayer: Boolean) {
                Log.e(TAG, "onDrag()")
            }

            override fun onFling(startX: Float, startY: Float, velocityX: Float, velocityY: Float, rootLayer: Boolean) {
                Log.e(TAG, "onFling()")
            }

            override fun cancelFling(rootLayer: Boolean) {
                Log.e(TAG, "cancelFling()")
            }

            override fun onScale(scaleFactor: Float, focusX: Float, focusY: Float, rootLayer: Boolean) {
                Log.e(TAG, "onScale()")
            }

            override fun onRotate(rotateDegree: Float, focusX: Float, focusY: Float, rootLayer: Boolean) {
                Log.e(TAG, "onRotate()")
            }

        }

        gestureDetector = CustomGestureDetector(getContext(), listener)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = parseAction(ev)

        when(action) {
            MotionEvent.ACTION_DOWN -> Log.e(TAG, "onInterceptTouchEvent : ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.e(TAG, "onInterceptTouchEvent : ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.e(TAG, "onInterceptTouchEvent : ACTION_UP")
            MotionEvent.ACTION_POINTER_DOWN -> Log.e(TAG, "onInterceptTouchEvent : ACTION_POINTER_DOWN")
            MotionEvent.ACTION_POINTER_UP -> Log.e(TAG, "onInterceptTouchEvent : ACTION_POINTER_UP")
            MotionEvent.ACTION_CANCEL -> Log.e(TAG, "onInterceptTouchEvent : ACTION_CANCEL")
            MotionEvent.ACTION_OUTSIDE -> Log.e(TAG, "onInterceptTouchEvent : ACTION_OUTSIDE")

        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        // dispatchTouchEvent 的 move 和 up 事件返回false或者true都不会影响后续触控事件的到来，只有 down事件 返回false, 后续的触控事件才不会再传过来
        // 要想View只处理down事件而move或up事件不处理，只能再parent view里面的onInterceptTouchEvent()里面做拦截判断
        // 一般不重写这个方法，只需要重写onTouchEvent()方法
        val action = parseAction(event)

        when (action) {
            MotionEvent.ACTION_DOWN ->  {
                Log.e(TAG, "dispatchTouchEvent : ACTION_DOWN")
//                return false
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e(TAG, "dispatchTouchEvent : ACTION_MOVE")
//                return false
            }
            MotionEvent.ACTION_UP ->  {
                Log.e(TAG, "dispatchTouchEvent : ACTION_UP")
//                return false
            }
            MotionEvent.ACTION_POINTER_DOWN -> Log.e(TAG, "dispatchTouchEvent : ACTION_POINTER_DOWN")
            MotionEvent.ACTION_POINTER_UP -> Log.e(TAG, "dispatchTouchEvent : ACTION_POINTER_UP")
            MotionEvent.ACTION_CANCEL -> Log.e(TAG, "dispatchTouchEvent : ACTION_CANCEL")
            MotionEvent.ACTION_OUTSIDE -> Log.e(TAG, "dispatchTouchEvent : ACTION_OUTSIDE")
        }

        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = parseAction(event)

        // onTouchEvent 的 move 和 up 事件返回false或者true都不会影响后续触控事件的到来，只有 down事件 返回false, 后续的触控事件才不会再传过来
        // 要想View只处理down事件而move或up事件不处理，只能再parent view里面的onInterceptTouchEvent()里面做拦截判断

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e(TAG, "onTouchEvent : ACTION_DOWN")
//                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e(TAG, "onTouchEvent : ACTION_MOVE")
//                return false        // 这里就算返回false，夫View的onTouchEvent也不会处理
            }
            MotionEvent.ACTION_UP -> {
                Log.e(TAG, "onTouchEvent : ACTION_UP")
//                return false      // 这里就算返回false，夫View的onTouchEvent也不会处理
            }
            MotionEvent.ACTION_POINTER_DOWN -> Log.e(TAG, "onTouchEvent : ACTION_POINTER_DOWN")
            MotionEvent.ACTION_POINTER_UP -> Log.e(TAG, "onTouchEvent : ACTION_POINTER_UP")
            MotionEvent.ACTION_CANCEL -> Log.e(TAG, "onTouchEvent : ACTION_CANCEL")
            MotionEvent.ACTION_OUTSIDE -> Log.e(TAG, "onTouchEvent : ACTION_OUTSIDE")
        }

        val returnValue = gestureDetector.onTouchEvent(event)

        Log.e(TAG, "returnValue : $returnValue" )

        return returnValue
//        return super.onTouchEvent(event)
    }
}