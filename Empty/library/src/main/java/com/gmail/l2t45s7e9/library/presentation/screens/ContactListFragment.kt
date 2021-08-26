package com.gmail.l2t45s7e9.library.presentation.screens


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.databinding.ContactListFragmentBinding
import com.gmail.l2t45s7e9.library.domain.ContactListViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelListFactory
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.gmail.l2t45s7e9.library.presentation.adapter.ContactItemDecorator
import com.gmail.l2t45s7e9.library.presentation.adapter.ContactListAdapter

import com.gmail.l2t45s7e9.library.viewbinding.viewBinding
import javax.inject.Inject
private const val ID_CONST:String = "id"
private const val COLOR_CONST:String = "color"
class ContactListFragment : Fragment(R.layout.contact_list_fragment) {

    @Inject
    lateinit var viewModelListFactory: ViewModelListFactory
    private val viewBinding: ContactListFragmentBinding by viewBinding()
    private val contactListViewModel: ContactListViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            viewModelListFactory
        ).get(ContactListViewModel::class.java)
    }
    private val adapter: ContactListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ContactListAdapter(onItemClickListener)
    }
    private val contactItemDecorator: ContactItemDecorator by lazy(LazyThreadSafetyMode.NONE) {
        ContactItemDecorator(
            (8 * resources.displayMetrics.density).toInt()
        )
    }
    private val onItemClickListener = ContactListAdapter.OnItemClickListener { contact: Contact ->
        val id = contact.id
        val color = contact.contactColor
        val bundle = Bundle()
        bundle.putString(ID_CONST, id)
        bundle.putInt(COLOR_CONST, color)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_contactListFragment_to_contactDetailsFragment, bundle)
    }
    private val onClickListener = View.OnClickListener { view: View? ->
        Navigation.findNavController(
            requireView()
        ).navigate(R.id.action_contactListFragment_to_mapFragment)
    }

    override fun onAttach(context: Context) {
        val app = requireActivity().application
        check(app is HasAppContainer)
        val contactListContainer = (app as HasAppContainer).appContainer()
            .plusContactListContainer()
        contactListContainer.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.addItemDecoration(contactItemDecorator)
        }

        contactListViewModel.listLiveData.observe(viewLifecycleOwner, { result: List<Contact?> ->
            adapter.submitList(result)
            viewBinding.apply {
                progressBar.visibility = View.GONE
                contactCount.text = result.size.toString()
            }
        })
        contactListViewModel.loadContactList("")
        viewBinding.apply {
            mapButton.setOnClickListener(onClickListener)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(filterPattern: String): Boolean {
                    contactListViewModel.loadContactList(filterPattern)
                    return false
                }
            })
        }
    }

}

