package com.lucas.soccerchallenge.features.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.base.ui.BaseFragment
import com.lucas.soccerchallenge.features.filter.FilterDialogViewModel
import com.lucas.soccerchallenge.features.results.adapter.ResultsAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_network_state.*
import javax.inject.Inject

class ResultsFragment : BaseFragment() {

    private lateinit var viewModel: ResultsViewModel

    private lateinit var filterViewModel: FilterDialogViewModel

    @Inject
    lateinit var adapter: ResultsAdapter

    companion object {

        fun newInstance(): ResultsFragment {
            return ResultsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultsViewModel::class.java)

        filterViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(FilterDialogViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
    }

    private fun initView() {
        list.adapter = adapter

        btn_retry.setOnClickListener {
            viewModel.fetchMatchResults()
        }
    }

    private fun subscribeToLiveData() {

        viewModel.getResultResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    hideView(btn_retry)
                    showView(loading)
                }
                is Resource.Error -> {
                    hideView(loading)
                    showView(btn_retry)
                    showToast(response.error.message)
                }
                is Resource.Success -> {
                    hideView(loading)
                    adapter.setList(response.data)
                }
            }
        })

        filterViewModel.filter.observe(viewLifecycleOwner, {
            adapter.setFilter(it)
        })
    }

    private fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
