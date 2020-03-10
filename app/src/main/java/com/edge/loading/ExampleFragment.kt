package com.edge.loading


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edge.loadingfragment.LoadingFragment
import com.edge.loadingfragment.OnRefreshClickListener
import kotlinx.android.synthetic.main.fragment_example.*

/**
 * A simple [Fragment] subclass.
 */
class ExampleFragment : LoadingFragment() {

    private val handler =Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView(view!! as ViewGroup)
        /*Default layout_loading_fail
        * this Layout contains refresh button and OnRefreshClickListener
        * If you custom this Layout use this method
        * setFailView(R.layout.layout_loading_fail)
        * */
        setProgressColor(R.color.colorAccent)
        loadingBtn.setOnClickListener {
            if (!isLoading){
                startLoading()
                loadingNoData()
            }
        }
        setOnRefreshClickListener(object : OnRefreshClickListener {
            override fun onRefreshClick() {
                startLoading()
                loadingFinish()
            }
        })
    }



    private fun loadingFinish(){
        handler.postDelayed({
            finishLoading(false)
        },2000)
    }

    private fun loadingNoData(){
        handler.postDelayed({
            finishLoading(true)
            loadingFail()
        },2000)
    }

    private fun loadingFail(){
        handler.postDelayed({
            failLoading()
        },2000)
    }
}
