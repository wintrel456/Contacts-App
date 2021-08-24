package com.gmail.l2t45s7e9.library.presentation.screens

import android.widget.CompoundButton
import com.gmail.l2t45s7e9.library.presentation.screens.AddressSearchFragment.OnChooseAddress
import javax.inject.Inject
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelDetailsFactory
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel
import androidx.appcompat.widget.SwitchCompat
import android.widget.TextView
import android.os.Bundle
import com.gmail.l2t45s7e9.library.R
import com.google.android.material.snackbar.Snackbar
import android.content.Context
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.l2t45s7e9.java.entity.Contact
import android.graphics.drawable.GradientDrawable
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gmail.l2t45s7e9.library.databinding.ContactDetailsFragmentBinding
import java.util.*

class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment),
    CompoundButton.OnCheckedChangeListener, OnChooseAddress {

    @Inject
    lateinit var viewModelDetailsFactory: ViewModelDetailsFactory
    private val contactDetailsViewModel: ContactDetailsViewModel by lazy {
        ViewModelProvider(
            this, viewModelDetailsFactory
        ).get(ContactDetailsViewModel::class.java)
    }
    private var color = 0
    private var position: String? = null
    private var date: GregorianCalendar? = null
    private var formatDate: String? = null
    private var addressString: String? = null
    private var addressState = false
    private var binding: ContactDetailsFragmentBinding? = null
    private val viewBinding get() = binding
    private val onClickListener = View.OnClickListener { view: View ->
        val bundle = Bundle()
        bundle.putString("id", position)
        if (!addressState) {
            val fragmentManager = fragmentManager
            val addressSearchFragment = AddressSearchFragment()
            addressSearchFragment.arguments = bundle
            addressSearchFragment.show(fragmentManager!!, "SearchAddress")
        } else {
            Navigation.findNavController(view)
                .navigate(R.id.action_contactDetailsFragment_to_mapFragment, bundle)
        }
    }

    override fun onChoose() {
        contactDetailsViewModel.loadContactDetails(position, color)
        Snackbar.make(requireView(), R.string.address_added, Snackbar.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        val app = requireActivity().application
        check(app is HasAppContainer)
        val contactDetailsContainer = (app as HasAppContainer).appContainer()
            .plusContactDetailsContainer()
        contactDetailsContainer.inject(this)
        super.onAttach(context)
        position = requireArguments().getString("id")
        color = requireArguments().getInt("color")
        formatDate = resources.getString(R.string.date_format_for_contact_details)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactDetailsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactDetailsViewModel.contactDetailsLiveData.observe(
            viewLifecycleOwner, { result: Contact ->
                position = result.id
                viewBinding?.avatar?.setColorFilter(result.contactColor)
                viewBinding?.userName?.text = result.name
                viewBinding?.userNumber?.text = result.firstNumber
                viewBinding?.secondUserNumber?.text = result.secondNumber
                viewBinding?.firstEmail?.text = result.firstEmail
                viewBinding?.secondEmail?.text = result.secondEmail
                addressString = result.contactAddress
                when(addressString.isNullOrEmpty()){
                    true -> {
                        addressState = false
                        viewBinding?.address?.text = getString(R.string.empty_address)
                        viewBinding?.addButton?.text = getString(R.string.button_add)
                    }
                    false ->  {
                        addressState = true
                        viewBinding?.address?.text = addressString
                        viewBinding?.addButton?.text = getString(R.string.see_on_map_label)
                    }

                }
                date = result.birthDate
                when(date != null){
                   true -> {
                       viewBinding?.birhDate?.text = String.format(
                           Locale.getDefault(),
                           formatDate!!,
                           date!![Calendar.DATE],
                           date!!.getDisplayName(
                               Calendar.MONTH, Calendar.LONG,
                               Locale.getDefault()
                           )
                       ).toUpperCase(Locale.ROOT)
                   }
                   false ->  viewBinding?.birhDate?.setText(R.string.empty_date)
                }
                color = result.contactColor
                setSwitchCompat()
            }
        )
        contactDetailsViewModel.loadContactDetails(position, color)
        val drawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.button,
            null
        ) as GradientDrawable?
        viewBinding?.addButton?.setOnClickListener(onClickListener)
        drawable?.setStroke(2, color)
        viewBinding?.userName?.isSelected = true
        viewBinding?.addButton?.background = drawable
    }

    private fun setSwitchCompat() {
        viewBinding?.notificationSwitch?.setOnCheckedChangeListener(this)
        viewBinding?.notificationSwitch?.isChecked = contactDetailsViewModel.status
    }


    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            viewBinding?.notificationSwitch?.thumbTintList = ColorStateList.valueOf(color)
            viewBinding?.notificationSwitch?.trackTintList = ColorStateList.valueOf(color).withAlpha(100)
            if (!contactDetailsViewModel.status) {
                contactDetailsViewModel.setNotification()
                Snackbar.make(
                    requireView(),
                    R.string.on_notification_toast_message,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            viewBinding?.notificationSwitch?.thumbTintList =
                ColorStateList.valueOf(resources.getColor(R.color.side_color))
            viewBinding?.notificationSwitch?.trackTintList =
                ColorStateList.valueOf(resources.getColor(R.color.second_side_color))
            if (contactDetailsViewModel.status) {
                contactDetailsViewModel.cancelNotification()
                Snackbar.make(
                    requireView(),
                    R.string.off_notification_toast_message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}