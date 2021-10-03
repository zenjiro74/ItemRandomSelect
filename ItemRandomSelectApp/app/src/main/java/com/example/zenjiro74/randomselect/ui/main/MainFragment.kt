package com.example.zenjiro74.randomselect.ui.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.zenjiro74.randomselect.R
import com.example.zenjiro74.randomselect.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collect

@ObsoleteCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val delayMilliSec = 1000L
    private var timestamp = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnItems.setOnClickListener { clickItems() }
        binding.btnAction.setOnClickListener {
            if (System.currentTimeMillis() - timestamp < delayMilliSec) {
                return@setOnClickListener
            }
            timestamp = System.currentTimeMillis()

            clickStartStop()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                if (it is Boolean) {
                    btnActionState(it)
                } else if (it is String) {
                    updateResultText(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun clickItems() {
        val items = viewModel.getItems()

        val editText = EditText(requireActivity().applicationContext)
        editText.setText(items)

        AlertDialog.Builder(requireActivity()).setTitle(R.string.items_title)
            .setMessage(R.string.items_text).setView(editText)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val newItems = editText.text.toString()
                viewModel.setItems(newItems = newItems)
            }.show()

    }

    private fun clickStartStop() {
        viewModel.actionState()
    }

    private fun btnActionState(isRoulette: Boolean) {
        if (isRoulette) {
            binding.btnAction.setText(R.string.btn_stop)
        } else {
            binding.btnAction.setText(R.string.btn_start)
        }
    }

    private fun updateResultText(result: String) {
        binding.txtResult.text = result
    }


}