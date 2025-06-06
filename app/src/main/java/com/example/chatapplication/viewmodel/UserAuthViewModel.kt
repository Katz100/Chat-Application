package com.example.chatapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.UiStates.AuthUiState
import com.example.chatapplication.data.repository.AuthenticationRepository
import com.example.chatapplication.domain.model.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class UserAuthViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val supabaseClient: SupabaseClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _response = MutableStateFlow<NetworkResult<Boolean>?>(null)
    val response: StateFlow<NetworkResult<Boolean>?> = _response

    init {
        viewModelScope.launch {
            supabaseClient.auth.sessionStatus.collect { newSession ->
                Log.e("AUTHSESSIONVIEW", newSession.toString())
                _uiState.update {
                    it.copy(session = newSession)

                }
                Log.e("AUTHSESSIONVIEW", "session value: ${uiState.value.session}")
            }
        }
    }

    fun logOut()  {
        viewModelScope.launch {
            val response = authenticationRepository.signOut()
            if (response is NetworkResult.Error) {
                //User tried to logout while offline.
                supabaseClient.auth.clearSession()
            } else {
                _response.value = response
            }
        }
    }
}