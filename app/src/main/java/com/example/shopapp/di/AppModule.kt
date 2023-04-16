package com.example.shopapp.di

import android.content.Context
import androidx.room.Room
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.Constant
import com.example.shopapp.room.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun shoppingDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingListDatabase :: class.java,Constant.CONS_LIST)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDiyabetApi(db : ShoppingListDatabase) = db.listDao()

    @Provides
    fun provideEntity() =CompletedList()

}