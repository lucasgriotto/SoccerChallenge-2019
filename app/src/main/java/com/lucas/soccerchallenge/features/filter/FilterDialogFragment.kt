package com.lucas.soccerchallenge.features.filter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.BaseDialogFragment
import com.lucas.soccerchallenge.features.filter.adapter.FilterAdapter
import kotlinx.android.synthetic.main.view_filter.view.*
import javax.inject.Inject

class FilterDialogFragment : BaseDialogFragment() {

    private lateinit var dialogView: View

    private lateinit var mainViewModel: FilterDialogViewModel

    @Inject
    lateinit var adapter: FilterAdapter

    companion object {

        fun newInstance(): FilterDialogFragment {
            return FilterDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogFullScreen)

        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(FilterDialogViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val builder = AlertDialog.Builder(requireActivity())
        dialogView = inflater.inflate(R.layout.view_filter, container)

        dialogView.apply {

            mainViewModel.getFilters()?.let {
                adapter.selectedCompetitions = HashSet(it)
            } ?: run {
                btn_select_all.isSelected = true
            }

            lay_all.setOnClickListener {
                btn_select_all.isSelected = !btn_select_all.isSelected
                if (btn_select_all.isSelected) {
                    adapter.onSelectAll()
                }
            }

            adapter.onFilterListener = {
                btn_select_all.isSelected = false
            }

            list.adapter = adapter

            btn_apply.setOnClickListener {
                val fil = adapter.selectedCompetitions
                mainViewModel.setFilters(fil)
                dismiss()
            }

            btn_close.setOnClickListener {
                dismiss()
            }

            root_layout.setOnClickListener {
                dismiss()
            }
        }

        builder.setView(dialogView)
        return dialogView
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            setLayout(width, height)
        }
    }
}