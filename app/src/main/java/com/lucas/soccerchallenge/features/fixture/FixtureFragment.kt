package com.lucas.soccerchallenge.features.fixture

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.networking.AppError
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.base.ui.BaseFragment
import com.lucas.soccerchallenge.base.ui.viewBinding
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.databinding.FragmentListBinding
import com.lucas.soccerchallenge.features.filter.FilterDialogViewModel
import com.lucas.soccerchallenge.features.fixture.adapter.FixtureAdapter
import javax.inject.Inject

class FixtureFragment : BaseFragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)

    private val viewModel: FixtureViewModel by viewModels { viewModelFactory }

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

        filterViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(FilterDialogViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
    }

    private fun initView() {
        binding.apply {
            mainList.adapter = adapter
            networkState.btnRetry.setOnClickListener {
                viewModel.fetchFixture()
            }
        }
    }

    private fun subscribeToLiveData() {

        viewModel.fixtureResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    displayLoadingState()
                }
                is Resource.Error -> {
                    displayErrorState(response.error)
                }
                is Resource.Success -> {
                    displaySuccessState(response.data)
                }
            }
        })

        filterViewModel.filter.observe(viewLifecycleOwner, {
            adapter.setFilter(it)
        })
    }

    private fun displayLoadingState() {
        binding.networkState.apply {
            hideView(btnRetry)
            showView(loading)
        }
    }

    private fun displayErrorState(error: AppError) {
        binding.networkState.apply {
            hideView(loading)
            showView(btnRetry)
        }
        showToast(error.message)
    }

    private fun displaySuccessState(matches: List<Match>) {
        hideView(binding.networkState.loading)
        adapter.setList(matches)
    }

    private fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
