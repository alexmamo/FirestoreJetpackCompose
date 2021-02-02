package ro.alexmamo.firestorejetpackcompose.products

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firestorejetpackcompose.data.DataOrException
import ro.alexmamo.firestorejetpackcompose.data.Product
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
        private val repository: ProductsRepository
): ViewModel() {
    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Product>, Exception>> = mutableStateOf(
            DataOrException(
                    listOf(),
                    Exception("")
            )
    )

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getProductsFromFirestore()
            loading.value = false
        }
    }
}