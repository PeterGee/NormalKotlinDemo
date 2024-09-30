package com.example.myapplication.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentViewBingdingBinding

/**
 * @Author qipeng
 * @Date 2024/9/30
 * @Desc fragmentViewBinding
 */
class ViewBindingFragment:Fragment() {
    private var mBinding: FragmentViewBingdingBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentViewBingdingBinding.inflate(inflater, container, false)
        initView()
        return mBinding?.root
    }

    private fun initView() {
      mBinding?.rvBindingView?.run {
          adapter = ViewBindingAdapter()
          setHasFixedSize(true)
          layoutManager = LinearLayoutManager(requireContext())
      }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}