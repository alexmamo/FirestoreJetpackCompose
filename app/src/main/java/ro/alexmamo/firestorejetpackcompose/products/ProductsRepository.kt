package ro.alexmamo.firestorejetpackcompose.products

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorejetpackcompose.data.DataOrException
import ro.alexmamo.firestorejetpackcompose.data.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    suspend fun getProductsFromFirestore(): DataOrException<List<Product>, Exception> {
        val dataOrException = DataOrException<List<Product>, Exception>()
        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                document.toObject(Product::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}