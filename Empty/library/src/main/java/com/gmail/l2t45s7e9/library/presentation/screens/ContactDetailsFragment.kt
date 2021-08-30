package com.gmail.l2t45s7e9.library.presentation.screens

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.databinding.ContactDetailsFragmentBinding
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelDetailsFactory
import com.gmail.l2t45s7e9.library.fragmentsState.ContactDetailsViewState
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.gmail.l2t45s7e9.library.presentation.screens.AddressSearchFragment.OnChooseAddress
import com.gmail.l2t45s7e9.library.viewbinding.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val ID_CONST: String = "id"
private const val COLOR_CONST: String = "color"

class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment),
    CompoundButton.OnCheckedChangeListener, OnChooseAddress {

    @Inject
    lateinit var viewModelDetailsFactory: ViewModelDetailsFactory
    private val contactDetailsViewModel: ContactDetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this, viewModelDetailsFactory
        ).get(ContactDetailsViewModel::class.java)
    }
    private val color: Int by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getInt(COLOR_CONST)
    }
    private val position: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[ID_CONST].toString()
    }

    private val drawable: GradientDrawable by lazy(LazyThreadSafetyMode.NONE) {
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.button,
            null
        ) as GradientDrawable
    }
    private var addressState = false
    private var notificationState = false
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
        contactDetailsViewModel.loadContactDetails(position, color)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactDetailsViewModel.contactDetailsLiveData.collect {
                    setContactDetails(it)
                }
            }
        }


        viewBinding.apply {
            addButton.setOnClickListener(onClickListener)
            drawable.setStroke(2, color)
            userName.isSelected = true
            addButton.background = drawable
        }
    }

    private fun setContactDetails(state: ContactDetailsViewState) {
        val result = state.contact
        result?.apply {
            viewBinding.apply {
                avatar.setColorFilter(result.contactColor)
                userName.text = result.name
                userNumber.text = result.firstNumber
                secondUserNumber.text = result.secondNumber
                firstEmail.text = result.firstEmail
                secondEmail.text = result.secondEmail
                birhDate.text = state.birthDate
                address.text = state.address
                addressState = state.addressState
                addButton.text = state.addButtonText
                notificationState = state.state
                setSwitchCompat(state.dateState)
            }
        }

    }

    private fun setSwitchCompat(dateState: Boolean) {
        viewBinding.apply {
            if (dateState) {
                notificationSwitch.setOnCheckedChangeListener(this@ContactDetailsFragment)
                notificationSwitch.isChecked = notificationState
            }
        }
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            viewBinding.apply {
                notificationSwitch.thumbTintList = ColorStateList.valueOf(color)
                notificationSwitch.trackTintList = ColorStateList.valueOf(color).withAlpha(100)
            }
            if (!notificationState) {
                contactDetailsViewModel.setNotification()
                Snackbar.make(
                    requireView(),
                    R.string.on_notification_toast_message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            viewBinding.apply {
                notificationSwitch.thumbTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.side_color))
                notificationSwitch.trackTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.second_side_color))
            }
            if (notificationState) {
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