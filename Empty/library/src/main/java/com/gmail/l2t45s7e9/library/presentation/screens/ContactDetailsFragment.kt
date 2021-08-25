package com.gmail.l2t45s7e9.library.presentation.screens

import android.widget.CompoundButton
import com.gmail.l2t45s7e9.library.presentation.screens.AddressSearchFragment.OnChooseAddress
import javax.inject.Inject
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelDetailsFactory
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel
import android.os.Bundle
import com.gmail.l2t45s7e9.library.R
import com.google.android.material.snackbar.Snackbar
import android.content.Context
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.gmail.l2t45s7e9.java.entity.Contact
import android.graphics.drawable.GradientDrawable
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gmail.l2t45s7e9.library.databinding.ContactDetailsFragmentBinding
import com.gmail.l2t45s7e9.library.viewbinding.viewBinding
import java.util.*
private const val ID_CONST:String = "id"
private const val COLOR_CONST:String = "color"
class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment),
    CompoundButton.OnCheckedChangeListener, OnChooseAddress {

    @Inject
    lateinit var viewModelDetailsFactory: ViewModelDetailsFactory
    private val contactDetailsViewModel: ContactDetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this, viewModelDetailsFactory
        ).get(ContactDetailsViewModel::class.java)
    }
    private val color:Int by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getInt(COLOR_CONST)
    }
    private val position: String by lazy(LazyThreadSafetyMode.NONE){
        requireArguments()[ID_CONST].toString()
    }
    private var date: GregorianCalendar? = null
    private val formatDate: String  by lazy(LazyThreadSafetyMode.NONE){
        resources.getString(R.string.date_format_for_contact_details)
    }
    private val drawable:GradientDrawable by lazy(LazyThreadSafetyMode.NONE) {
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.button,
            null
        ) as GradientDrawable
    }
    private var addressString: String? = null
    private var addressState = false
    private val viewBinding: ContactDetailsFragmentBinding by viewBinding()

    private val onClickListener = View.OnClickListener { view: View ->
        val bundle = Bundle()
        bundle.putString(ID_CONST, position)
        if (!addressState) {
            val fragmentManager = childFragmentManager
            val addressSearchFragment = position.let { AddressSearchFragment.getInstance(it) }
            addressSearchFragment.show(fragmentManager, "SearchAddress")
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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactDetailsViewModel.contactDetailsLiveData.observe(
            viewLifecycleOwner, { result: Contact ->
                viewBinding.apply {
                    avatar.setColorFilter(result.contactColor)
                    userName.text = result.name
                    userNumber.text = result.firstNumber
                    secondUserNumber.text = result.secondNumber
                    firstEmail.text = result.firstEmail
                    secondEmail.text = result.secondEmail
                    addressString = result.contactAddress
                    date = result.birthDate
                    date?.apply {
                        birhDate.text = String.format(
                            Locale.getDefault(),
                            formatDate,
                            this[Calendar.DATE],
                            this.getDisplayName(
                                Calendar.MONTH, Calendar.LONG,
                                Locale.getDefault()
                            )
                        ).toUpperCase(Locale.ROOT)
                    }?:birhDate.setText(R.string.empty_date)

                    when(addressString.isNullOrEmpty()){
                        true -> {
                            addressState = false
                            address.text = getString(R.string.empty_address)
                            viewBinding.addButton.text = getString(R.string.button_add)
                        }
                        false ->  {
                            addressState = true
                            address.text = addressString
                            viewBinding.addButton.text = getString(R.string.see_on_map_label)
                        }

                    }
                    setSwitchCompat()
                    addButton.setOnClickListener(onClickListener)
                    drawable.setStroke(2, color)
                    userName.isSelected = true
                    addButton.background = drawable
                }
            }
        )
        contactDetailsViewModel.loadContactDetails(position, color)
    }

    private fun setSwitchCompat() {
        viewBinding.apply{
            notificationSwitch.setOnCheckedChangeListener(this@ContactDetailsFragment)
            notificationSwitch.isChecked = contactDetailsViewModel.status
        }
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            viewBinding.apply {
                notificationSwitch.thumbTintList = ColorStateList.valueOf(color)
                notificationSwitch.trackTintList = ColorStateList.valueOf(color).withAlpha(100)
            }
            if (!contactDetailsViewModel.status) {
                contactDetailsViewModel.setNotification()
                Snackbar.make(
                    requireView(),
                    R.string.on_notification_toast_message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            viewBinding.apply {
                notificationSwitch.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.side_color))
                notificationSwitch.trackTintList = ColorStateList.valueOf(resources.getColor(R.color.second_side_color))
            }
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

}