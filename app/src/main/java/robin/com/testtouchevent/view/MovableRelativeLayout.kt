package robin.com.testtouchevent.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import robin.com.testtouchevent.R
import robin.com.testtouchevent.utils.parseAction


/**
 * Created by Robin Yeung on 10/18/18.
 */
class MovableRelativeLayout : RelativeLayout {

    companion object {
        private val TAG = "MovableRelativeLayout"
    }

    private var mContext: Context? = null

    var adView: ImageView? = null
        internal set

    internal var ivClose: ImageView? = null

    private var mFirstDownX: Float = 0.toFloat()
    private var mFirstDownY: Float = 0.toFloat()
    private var mTransDownX: Float = 0.toFloat()
    private var mTransDownY: Float = 0.toFloat()
    private var mSlope: Int = 0

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        mContext = context

        LayoutInflater.from(mContext).inflate(R.layout.view_movable_relativelayout, this)

        adView = findViewById(R.id.ivAdvertisement)
        ivClose = findViewById(R.id.ivClose)

        isFocusableInTouchMode = true
        isClickable = true
        isFocusable = true
        mSlope = ViewConfiguration.get(getContext()).scaledTouchSlop

        adView?.setOnClickListener {
            Log.e(TAG, "点击事件执行")
        }

        ivClose!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                visibility = View.GONE
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = parseAction(event)

        when(action) {
            MotionEvent.ACTION_DOWN -> Log.e(TAG, "dispatchTouchEvent : ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.e(TAG, "dispatchTouchEvent : ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.e(TAG, "dispatchTouchEvent : ACTION_UP")
            MotionEvent.ACTION_POINTER_DOWN -> Log.e(TAG, "dispatchTouchEvent : ACTION_POINTER_DOWN")
            MotionEvent.ACTION_POINTER_UP -> Log.e(TAG, "dispatchTouchEvent : ACTION_POINTER_UP")
            MotionEvent.ACTION_CANCEL -> Log.e(TAG, "dispatchTouchEvent : ACTION_CANCEL")
            MotionEvent.ACTION_OUTSIDE -> Log.e(TAG, "dispatchTouchEvent : ACTION_OUTSIDE")
        }

        return super.dispatchTouchEvent(event)
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

        val parent = parent as ViewGroup
        requestDisallowIntercept(parent)

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mFirstDownX = ev.rawX
                mFirstDownY = ev.rawY

                mTransDownX = mFirstDownX - translationX
                mTransDownY = mFirstDownY - translationY
            }
            MotionEvent.ACTION_MOVE -> {
                val xDelta = ev.rawX - mFirstDownX
                val yDelta = ev.rawY - mFirstDownY
                if (Math.sqrt((xDelta * xDelta + yDelta * yDelta).toDouble()) > mSlope) {
                    return true
                }
            }
        }
        return false
//        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = parseAction(event)

        when(action) {
            MotionEvent.ACTION_DOWN -> Log.e(TAG, "onTouchEvent : ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.e(TAG, "onTouchEvent : ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.e(TAG, "onTouchEvent : ACTION_UP")
            MotionEvent.ACTION_POINTER_DOWN -> Log.e(TAG, "onTouchEvent : ACTION_POINTER_DOWN")
            MotionEvent.ACTION_POINTER_UP -> Log.e(TAG, "onTouchEvent : ACTION_POINTER_UP")
            MotionEvent.ACTION_CANCEL -> Log.e(TAG, "onTouchEvent : ACTION_CANCEL")
            MotionEvent.ACTION_OUTSIDE -> Log.e(TAG, "onTouchEvent : ACTION_OUTSIDE")

        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val parent = parent as ViewGroup

                var transX = event.rawX - mTransDownX
                if (transX > 0) {
                    transX = 0f
                }
                if (transX < width - parent.width) {
                    transX = (width - parent.width).toFloat()
                }
                translationX = transX

                var transY = event.rawY - mTransDownY
                if (transY > 0) {
                    transY = 0f
                }
                if (transY < height - parent.height) {
                    transY = (height - parent.height).toFloat()
                }
                translationY = transY
            }
        }
        return true
    }

    private fun requestDisallowIntercept(viewGroup: ViewGroup) {
        viewGroup.requestDisallowInterceptTouchEvent(true)
        if (viewGroup is ViewPager) {
            return
        } else if (viewGroup.parent is ViewGroup) {
            requestDisallowIntercept(viewGroup.parent as ViewGroup)
        }
    }

    fun setAdOnclickListener(onclickListener: View.OnClickListener) {
        adView!!.setOnClickListener(onclickListener)
    }

    fun setCloseOnclickListener(onclickListener: View.OnClickListener) {
        ivClose!!.setOnClickListener(onclickListener)
    }
}