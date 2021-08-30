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

private const val ID_CONST: String = "id"

class AddressSearchFragment : DialogFragment(R.layout.address_search_fragment) {
    companion object {
        fun getInstance(position: String): AddressSearchFragment {
            val bundle = Bundle()
            bundle.putString(ID_CONST, position)
            val address = AddressSearchFragment()
            address.arguments = bundle
            return address
        }
    }

    @Inject
    lateinit var addressSearchFactory: ViewModelAddressSearchFactory
    private var searchAddressView: SearchView? = null
    private lateinit var listView: RecyclerView
    private val viewModel: AddressSearchViewModel by lazy {
        ViewModelProvider(this, addressSearchFactory).get(AddressSearchViewModel::class.java)
    }
    private val id: String by lazy {
        requireArguments().getString(ID_CONST).toString()
    }

    interface OnChooseAddress {
        fun onChoose()
    }

    private val onItemClick = object : AddressSearchAdapter.OnItemClickListener {
        override fun onItemClicked(string: String?) {
            viewModel.addAddressForContact(string, id)
            (requireParentFragment() as? ContactDetailsFragment)?.also { contactDetailsFragment ->
                dismiss()
                contactDetailsFragment.onChoose()
            }
        }
    }

    override fun onAttach(context: Context) {
        val app = context.applicationContext as Application
        check(app is HasAppContainer)
        (app as HasAppContainer).apply {
            appContainer().plusAddressSearchContainer().inject(this@AddressSearchFragment)
        }
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAddressView = view.findViewById(R.id.addressSearchView)
        listView = view.findViewById(R.id.addressList)
        val adapter = AddressSearchAdapter(onItemClick)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(context)
        viewModel.listLiveData.observe(
            this,
            {
                adapter.submitList(it)
            }
        )
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
