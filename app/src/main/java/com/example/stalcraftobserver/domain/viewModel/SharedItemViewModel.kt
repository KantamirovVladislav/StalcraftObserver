package com.example.stalcraftobserver.domain.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedItemViewModel @Inject constructor() : ViewModel() {
    private val _weaponId = MutableStateFlow<String?>(null)
    val weaponId: StateFlow<String?> = _weaponId

    private val _armorId = MutableStateFlow<String?>(null)
    val armorId: StateFlow<String?> = _armorId

    fun setWeaponId(id: String) {
        _weaponId.value = id
    }

    fun setArmorId(id: String) {
        _armorId.value = id
    }
}
