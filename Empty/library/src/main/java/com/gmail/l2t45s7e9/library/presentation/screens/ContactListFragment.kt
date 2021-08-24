package com.gmail.l2t45s7e9.library.presentation.screens


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class ContactListFragment : Fragment(R.layout.contact_list_fragment) {

    @Inject
    lateinit var viewModelListFactory: ViewModelListFactory
    private var binding: ContactListFragmentBinding? = null
    private val viewBinding get() = binding
    private val contactListViewModel: ContactListViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelListFactory
        ).get(ContactListViewModel::class.java)
    }
    private val adapter: ContactListAdapter by lazy{
        ContactListAdapter(onItemClickListener)
    }
    private val onItemClickListener = ContactListAdapter.OnItemClickListener { contact: Contact ->
        val id = contact.id
        val color = contact.contactColor
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putInt("color", color)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactListFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactItemDecorator = ContactItemDecorator(
            (8 * resources.displayMetrics.density).toInt()
        )
        viewBinding?.recyclerView?.adapter = adapter
        viewBinding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        viewBinding?.recyclerView?.addItemDecoration(contactItemDecorator)

        contactListViewModel.listLiveData.observe(viewLifecycleOwner, { result: List<Contact?> ->
            adapter.submitList(result)
            viewBinding?.contactCount?.text = result.size.toString()
            viewBinding?.progressBar?.visibility = View.GONE
        })
        contactListViewModel.loadContactList("")
        viewBinding?.mapButton?.setOnClickListener(onClickListener)
        val searchView = view.findViewById<SearchView>(R.id.searchView)
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}