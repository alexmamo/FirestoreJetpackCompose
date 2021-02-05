package ro.alexmamo.firestorejetpackcompose.products

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ro.alexmamo.firestorejetpackcompose.composables.CircularProgressBar
import ro.alexmamo.firestorejetpackcompose.composables.ProductCard
import ro.alexmamo.firestorejetpackcompose.data.DataOrException
import ro.alexmamo.firestorejetpackcompose.data.Product

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
            Text(
                    text = e.message!!,
                    modifier = Modifier.padding(16.dp)
            )
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