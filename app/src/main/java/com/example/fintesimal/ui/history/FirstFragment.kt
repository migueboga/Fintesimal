package com.example.fintesimal.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintesimal.R
import com.example.fintesimal.data.db.entities.Address
import com.example.fintesimal.databinding.FirstFragmentBinding
import com.example.fintesimal.util.Coroutines
import kotlinx.android.synthetic.main.first_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FirstFragment : Fragment(), KodeinAware, FirstListener, FirstAdapter.Interaction {

    override val kodein by kodein()
    private val factory: FirstViewModelFactory by instance()
    private lateinit var viewModel: FirstViewModel
    lateinit var firstAdapter: FirstAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val bindUI: FirstFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        viewModel = ViewModelProvider(this, factory).get(FirstViewModel::class.java)

        bindUI.viewModel = viewModel
        viewModel.listener = this
        return bindUI.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firstAdapter = FirstAdapter(this@FirstFragment)
        recycler_first.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = firstAdapter
        }
        viewModel.onLoad()
    }

    override fun onVisitCalculate(visits: Int) {
        Coroutines.main {
            description_first.text = String.format(getString(R.string.descriptor_first),visits)
        }
    }

    override fun onSuccess(arrayOfAddress: List<Address>) {
        Coroutines.main {
            firstAdapter.submitList(arrayOfAddress)
        }
    }

    override fun onFailure() {

    }

    override fun onItemSelected(item: Address) {
        viewModel.onShowDetail(requireView(), item)
    }
}