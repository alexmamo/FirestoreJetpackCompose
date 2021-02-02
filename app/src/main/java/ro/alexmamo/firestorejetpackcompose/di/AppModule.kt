package ro.alexmamo.firestorejetpackcompose.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Query.*
import com.google.firebase.firestore.Query.Direction.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.alexmamo.firestorejetpackcompose.utils.Constants.NAME_PROPERTY
import ro.alexmamo.firestorejetpackcompose.utils.Constants.PRODUCTS_COLLECTION
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQueryProductsByName() = FirebaseFirestore.getInstance()
        .collection(PRODUCTS_COLLECTION)
        .orderBy(NAME_PROPERTY, ASCENDING)
}