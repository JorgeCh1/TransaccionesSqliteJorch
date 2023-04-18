package uca.jorch.roomwordsamplejorch

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NombreViewModel(private val repository: NombreRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Nombre>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(nombre: Nombre) = viewModelScope.launch {
        repository.insert(nombre)
    }
}

class NombreViewModelFactory(private val repository: NombreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NombreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NombreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}