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
import kotlinx.android.synthetic.main.layout_no_data.view.*

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
    private var noDataTextColor : Int = R.color.black
    private var noDataBackgroundColor : Int = R.color.white
    private var noDataLayout : View? = null
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
        setNoDataLayout(R.layout.layout_no_data)
    }

    protected fun setOnRefreshClickListener(onRefreshClickListener: OnRefreshClickListener) {
        this.onRefreshClickListener = onRefreshClickListener
    }

    protected fun setFailView(@LayoutRes layoutResId: Int) {
        failLayout = LayoutInflater.from(activity).inflate(layoutResId, null)
        failLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
        failLayout?.failTv?.setTextColor(ContextCompat.getColor(activity!!,failTextColor))
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

    protected fun setNoDataLayout(@LayoutRes layoutResId: Int){
        noDataLayout = LayoutInflater.from(activity).inflate(layoutResId, null)
        noDataLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,noDataBackgroundColor))
        noDataLayout?.noDataTv?.setTextColor(ContextCompat.getColor(activity!!,noDataTextColor))
        noDataLayout?.layoutParams = backgroundParams
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
        failLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,failBackgroundColor))
    }
    protected fun setFailTextColor(@ColorRes colorResId: Int){
        failTextColor =  colorResId
        failLayout?.failTv?.setTextColor(ContextCompat.getColor(activity!!,failTextColor))
    }

    protected fun setRefreshBtnColor(@ColorRes colorResId: Int){
        refreshBtnTextColor = colorResId
        failLayout?.refreshBtn?.setTextColor(ContextCompat.getColor(activity!!,refreshBtnTextColor))
    }

    protected fun setNoDataTextColor(@ColorRes colorResId: Int){
        noDataTextColor =  colorResId
        noDataLayout?.noDataTv?.setTextColor(ContextCompat.getColor(activity!!,noDataTextColor))
    }

    protected fun setNoDataBackgroundColor(@ColorRes colorResId: Int){
        noDataBackgroundColor = colorResId
        noDataLayout?.setBackgroundColor(ContextCompat.getColor(activity!!,noDataBackgroundColor))
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

    protected fun finishLoading(noData: Boolean) {
        isLoading = false
        if (backgroundView!=null) {
           if (noData){
              noDataLoading()
           }else{
               showOriginContent()
               originView?.removeView(backgroundView)
               originView?.removeView(failLayout)
           }
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


    protected fun noDataLoading(){
        isLoading = false
        if (backgroundView!=null) {
            originView?.removeView(backgroundView)
        }
        if (noDataLayout!=null && noDataLayout!!.parent != null){
            noDataLayout?.visibility=View.VISIBLE
        } else{
            originView?.addView(noDataLayout)
        }
    }
}
