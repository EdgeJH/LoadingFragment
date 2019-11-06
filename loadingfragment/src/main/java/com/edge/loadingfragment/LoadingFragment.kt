package com.edge.loadingfragment


import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_loading_fail.view.*

/**
 * A simple [Fragment] subclass.
 */


open class LoadingFragment : Fragment() {

    private var originView: ViewGroup? = null
    private lateinit var progress: ProgressBar
    private var backgroundView: FrameLayout? = null
    private var progressColor: Int = R.color.black
    private var backgroundColor : Int = R.color.white
    private var failBackgroundColor : Int = R.color.white
    private var refreshBtnTextColor : Int = R.color.black
    private var failTextColor : Int = R.color.black
    private var failLayout: View? = null
    private val backgroundParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    var isLoading : Boolean = false
    private var onRefreshClickListener: OnRefreshClickListener? = null

    protected fun setView(view: ViewGroup) {
        originView = view
        initLoadingView()
        setFailView(R.layout.layout_loading_fail)
    }

    protected fun setOnRefreshClickListener(onRefreshClickListener: OnRefreshClickListener) {
        this.onRefreshClickListener = onRefreshClickListener
    }

    protected fun setFailView(@LayoutRes layoutResId: Int) {
        failLayout = LayoutInflater.from(activity).inflate(layoutResId, null)
        failLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
        failLayout?.refreshBtn?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
        failLayout?.fail_text?.setTextColor(ContextCompat.getColor(activity!!,failTextColor))
        failLayout?.refreshBtn?.setTextColor(ContextCompat.getColor(activity!!,refreshBtnTextColor))
        failLayout?.layoutParams = backgroundParams
        when (layoutResId) {
            R.layout.layout_loading_fail -> {
                failLayout?.refreshBtn?.setOnClickListener {
                    if (onRefreshClickListener != null) {
                        onRefreshClickListener!!.onRefreshClick()
                    }
                }
            }
        }
    }

    protected fun setProgressColor(@ColorRes colorResId: Int) {
        progressColor = colorResId
    }

    protected fun setBackgroundColor(@ColorRes colorResId: Int){
        backgroundColor = colorResId
        backgroundView?.setBackgroundColor(ContextCompat.getColor(activity!!,backgroundColor))
    }
    protected fun setFailBackgroundColor(@ColorRes colorResId: Int){
        failBackgroundColor = colorResId
        failLayout?.refreshBtn?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
        failLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
    }
    protected fun setFailTextColor(@ColorRes colorResId: Int){
        failTextColor =  colorResId
        failLayout?.fail_text?.setTextColor(ContextCompat.getColor(activity!!,failTextColor))
    }

    protected fun setRefreshBtnColor(@ColorRes colorResId: Int){
        refreshBtnTextColor = colorResId
        failLayout?.refreshBtn?.setTextColor(ContextCompat.getColor(activity!!,refreshBtnTextColor))
    }

    private fun initLoadingView() {
        backgroundView = FrameLayout(activity!!)
        backgroundView?.setBackgroundColor(ContextCompat.getColor(activity!!,backgroundColor))
        backgroundView?.layoutParams = backgroundParams
        val progressParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        progressParams.gravity = Gravity.CENTER
        progress = ProgressBar(activity)
        progress.layoutParams = progressParams
        backgroundView?.addView(progress)
    }

    protected fun startLoading() {
        isLoading = true
        if (originView != null) {
            progress.indeterminateDrawable.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(activity!!, progressColor),
                    PorterDuff.Mode.SRC_IN
                )
            hideOriginContent()
            originView!!.addView(backgroundView)
        }
    }

    private fun hideOriginContent() {
        if (originView != null) {
            for (i in 0 until originView!!.childCount) {
                originView!!.getChildAt(i).visibility = View.GONE
            }
        }
    }

    private fun showOriginContent() {
        if (originView != null) {
            for (i in 0 until originView!!.childCount) {
                originView!!.getChildAt(i).visibility = View.VISIBLE
            }
        }
    }

    protected fun finishLoading() {
        isLoading = false
        if (backgroundView!=null) {
            showOriginContent()
            originView?.removeView(failLayout)
            originView?.removeView(backgroundView)
        }
    }
    protected fun failLoading() {
        isLoading = false
        if (backgroundView!=null) {
            originView?.removeView(backgroundView)
        }
        if (failLayout!=null && failLayout!!.parent != null){
            failLayout?.visibility=View.VISIBLE
        } else{
            originView?.addView(failLayout)
        }

    }
}
