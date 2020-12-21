package com.lucas.soccerchallenge.features.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.base.ui.BaseFragment
import com.lucas.soccerchallenge.features.filter.FilterDialogViewModel
import com.lucas.soccerchallenge.features.fixture.adapter.FixtureAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_network_state.*
import javax.inject.Inject

class FixtureFragment : BaseFragment() {

    private lateinit var viewModel: FixtureViewModel

    private lateinit var filterViewModel: FilterDialogViewModel

    @Inject
    lateinit var adapter: FixtureAdapter

    companion object {

        fun newInstance(): FixtureFragment {
            return FixtureFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FixtureViewModel::class.java)

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
            viewModel.getFixture()
        }
    }

    private fun subscribeToLiveData() {

        viewModel.getFixtureResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    hideView(btn_retry)
                    showView(loading)
                }
                is Resource.Error -> {
                    hideView(loading)
                    showView(btn_retry)
                    showToast(response.message)
                }
                is Resource.Success -> {
                    hideView(loading)
                    adapter.setList(response.data)
                }
            }
        })

        filterViewModel.filter.observe(viewLifecycleOwner, Observer {
            adapter.setFilter(it)
        })
    }

    private fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
