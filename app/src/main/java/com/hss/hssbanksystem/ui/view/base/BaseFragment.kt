package com.hss.hssbanksystem.ui.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.core.RetrofitHelper
import com.hss.hssbanksystem.data.repository.BaseRepository
import com.hss.hssbanksystem.ui.viewmodel.base.BaseViewModel
import com.hss.hssbanksystem.ui.viewmodel.base.ViewModelFactory

abstract class BaseFragment<V: BaseViewModel, B: ViewBinding, R: BaseRepository>: Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: V
    protected lateinit var dataStoreHelper: DataStoreHelper
    protected val retrofitHelper = RetrofitHelper()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataStoreHelper = DataStoreHelper(requireContext())
        binding = getViewBinding(inflater, container)
        viewModel = ViewModelProvider(this, ViewModelFactory(getRepository())).get(getViewModel())
        return binding.root
    }

    abstract fun getViewModel(): Class<V>
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getRepository(): R

}