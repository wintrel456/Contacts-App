package com.gmail.l2t45s7e9.library.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val contactListInteractor: ContactListInteractor,
    private val schedulersProvider: SchedulersProvider
) : ViewModel() {
    private val contactListMutableLiveData = MutableLiveData<List<Contact>>()
    private val disposable = CompositeDisposable()
    val listLiveData: LiveData<List<Contact>> get() = contactListMutableLiveData
    fun loadContactList(filterPattern: String) = viewModelScope.launch {
        contactListInteractor.getContactListRepo(filterPattern).map {
            contactListMutableLiveData.value = it
        }.single()
    }
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}