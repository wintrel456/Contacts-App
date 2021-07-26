package com.gmail.l2t45s7e9.library.presentation.screens

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.domain.AddressSearchViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelAddressSearchFactory
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.gmail.l2t45s7e9.library.presentation.adapter.AddressSearchAdapter
import javax.inject.Inject


class AddressSearchFragment : DialogFragment(R.layout.address_search_fragment) {

    @Inject
    lateinit var addressSearchFactory: ViewModelAddressSearchFactory
    private var searchAddressView: SearchView? = null
    private lateinit var listView: RecyclerView
    private val viewModel: AddressSearchViewModel by lazy {
        ViewModelProvider(this, addressSearchFactory).get(AddressSearchViewModel::class.java)
    }
    private lateinit var id: String

    interface OnChooseAddress {
        fun onChoose()
    }

    private val onItemClick = object : AddressSearchAdapter.OnItemClickListener {
        override fun onItemClicked(string: String?) {
            viewModel.addAddressForContact(string, id)
            val contactFrag = fragmentManager?.findFragmentById(R.id.navHost) as ContactDetailsFragment
            dismiss()
            contactFrag.onChoose()
        }
    }

    override fun onAttach(context: Context) {
        val app = context.applicationContext as Application
        check(app is HasAppContainer)
        (app as HasAppContainer).apply {
            appContainer().plusAddressSearchContainer().inject(this@AddressSearchFragment)
        }
        super.onAttach(context)
        id = requireArguments().getString("id").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAddressView = view.findViewById(R.id.addressSearchView)
        listView = view.findViewById(R.id.addressList)
        val adapter = AddressSearchAdapter(onItemClick)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(context)
        viewModel.listLiveData.observe(this, {
            adapter.submitList(it)
        })
        searchAddressView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(search: String?): Boolean {
                search?.also {
                    viewModel.textFilter(search)
                }
                return false
            }
        })

    }

    override fun onDestroyView() {
        listView.adapter = null
        listView.layoutManager = null
        searchAddressView = null
        super.onDestroyView()
    }
}