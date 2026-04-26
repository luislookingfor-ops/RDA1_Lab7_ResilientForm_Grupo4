package com.example.formularioregistro.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.formularioregistro.data.UserPreferences

class FormViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val userPreferences: UserPreferences
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormViewModel(
                stateHandle = handle,
                userPrefs = userPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}