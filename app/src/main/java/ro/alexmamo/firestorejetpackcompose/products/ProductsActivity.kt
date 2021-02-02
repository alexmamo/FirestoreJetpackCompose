package ro.alexmamo.firestorejetpackcompose.products

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ro.alexmamo.firestorejetpackcompose.composables.CircularProgressBar
import ro.alexmamo.firestorejetpackcompose.composables.ProductCard
import ro.alexmamo.firestorejetpackcompose.data.DataOrException
import ro.alexmamo.firestorejetpackcompose.data.Product
import ro.alexmamo.firestorejetpackcompose.utils.Constants.TAG

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ProductsActivity : AppCompatActivity() {
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataOrException = viewModel.data.value
            ProductsActivity(dataOrException)
        }
    }

    @Composable
    fun ProductsActivity(dataOrException: DataOrException<List<Product>, Exception>) {
        val products = dataOrException.data
        products?.let {
            LazyColumn {
                items(
                        items = products
                ) { product ->
                    ProductCard(product = product)
                }
            }
        }

        val e = dataOrException.e
        e?.let {
            Log.d(TAG, e.message!!)
        }

        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(
                    isDisplayed = viewModel.loading.value
            )
        }
    }
}