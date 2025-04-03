package com.example.c7_2

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GitViewModel : ViewModel(){
    private val _gitState = MutableStateFlow<GitState>(GitState.Initial)
    val gitState: StateFlow<GitState> = _gitState
    private val allRepos = mutableListOf<GitResponse>()
    private var currentPage = 1
    private var perPage = 30
    private val prevName =  mutableStateOf("")
    fun fetchGit(name: String) {
        if(!name.equals(prevName.value)){
            allRepos.clear()
            prevName.value = name;
        }
        viewModelScope.launch {
            _gitState.value = GitState.Loading
            try {
                val gitResponse = ApiClient.apiSerive.getGit(name,perPage,currentPage)
                Log.d("GitViewModel", "Fetched repos: $gitResponse")

                if (gitResponse.isNotEmpty()) {
                    allRepos.addAll(gitResponse)
                    currentPage++
                    _gitState.value = GitState.Success(allRepos)
                }

            } catch (e: Exception) {
                _gitState.value = GitState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun loadMoreRepos(name: String) {
        fetchGit(name)
    }
    sealed class GitState {
        object Initial : GitState()
        object Loading : GitState()
        data class Success(val gitResponse: List<GitResponse>) : GitState()
        data class Error(val errorMessage: String) : GitState()
    }
}