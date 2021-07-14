package com.gmail.l2t45s7e9.library.domain.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.library.domain.MapViewModel

open class ViewModelMapFactory(_context: Context) : ViewModelProvider.Factory {

    private var mapViewModel: MapViewModel = MapViewModel(_context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return mapViewModel as T
    }
}