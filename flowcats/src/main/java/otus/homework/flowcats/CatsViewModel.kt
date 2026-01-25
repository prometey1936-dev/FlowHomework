package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow  = MutableStateFlow<Result<Fact>>(Result.Success(Fact()))
    val catsStateFlow: StateFlow<Result<Fact>> = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect { fact ->
                    _catsStateFlow.value = Result.Success(fact)
                }
            } catch (e: Exception) {
                _catsStateFlow.value = Result.Error(e)
            }
        }
    }
}


class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}