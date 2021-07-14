package com.gmail.l2t45s7e9.library.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.domain.AddressSerachViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelAddressSearchFactory
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.gmail.l2t45s7e9.library.presentation.adapter.AddressSearchAdapter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AddressSearchFragment(
        private var onChooseAddress: OnChooseAddress, var id: String
) : DialogFragment(R.layout.address_search_fragment) {

    @Inject
    lateinit var addressSearchFactory: ViewModelAddressSearchFactory
    private lateinit var searchAddressView: SearchView
    private lateinit var listView: RecyclerView
    private lateinit var adapter: AddressSearchAdapter
    private lateinit var viewModel: AddressSerachViewModel


    interface OnChooseAddress {
        fun onChoose(string: String?)
    }

    val onItemClick = object : AddressSearchAdapter.OnItemClickListener {
        override fun onItemClicked(string: String?, view: View?) {
            viewModel.addAddressForContact(string, id)
            onChooseAddress.onChoose(string)
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        val app = requireActivity().application
        check(app is HasAppContainer)
        val addressSearchContainer = (app as HasAppContainer).appContainer()
                .plusAddressSearchContainer()
        addressSearchContainer.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAddressView = view.findViewById(R.id.addressSearchView)
        listView = view.findViewById(R.id.addressList)
        adapter = AddressSearchAdapter(onItemClick)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProvider(this, addressSearchFactory).get(AddressSerachViewModel::class.java)
        viewModel.listLiveData.observe(this, {
            adapter.submitList(it)
        })
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchAddressView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    subscriber.onNext(p0!!)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    subscriber.onNext(p0!!)
                    return false
                }

            })
        })
                .map { text -> text.toLowerCase().trim() }
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinct()
                .filter { text -> text.isNotBlank() }
                .subscribe { text ->
                    viewModel.getStartList(text)
                }

    }
}