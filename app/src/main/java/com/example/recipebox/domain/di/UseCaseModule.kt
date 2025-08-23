package com.example.recipebox.domain.di

import com.example.recipebox.domain.repository.CollectionRepository
import com.example.recipebox.domain.repository.RecipeRepository
import com.example.recipebox.domain.usecase.recipe.AddRecipeUseCase
import com.example.recipebox.domain.usecase.recipe.DeleteRecipeUseCase
import com.example.recipebox.domain.usecase.recipe.GetRecipeByIdUseCase
import com.example.recipebox.domain.usecase.recipe.GetRecipesUseCase
import com.example.recipebox.domain.usecase.recipe.RecipeUseCases
import com.example.recipebox.domain.usecase.recipe.SearchRecipesUseCase
import com.example.recipebox.domain.usecase.recipe.UpdateRecipeUseCase
import com.example.recipebox.domain.usecase.collection.AddRecipeToCollectionUseCase
import com.example.recipebox.domain.usecase.collection.CollectionUseCases
import com.example.recipebox.domain.usecase.collection.CreateCollectionUseCase
import com.example.recipebox.domain.usecase.collection.DeleteCollectionUseCase
import com.example.recipebox.domain.usecase.collection.GetCollectionsUseCase
import com.example.recipebox.domain.usecase.collection.GetCollectionsWithRecipesUseCase
import com.example.recipebox.domain.usecase.collection.RemoveRecipeFromCollectionUseCase
import com.example.recipebox.domain.usecase.collection.UpdateCollectionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideRecipeUseCases(repository: RecipeRepository): RecipeUseCases {
        return RecipeUseCases(
            addRecipe = AddRecipeUseCase(repository),
            getRecipes = GetRecipesUseCase(repository),
            searchRecipes = SearchRecipesUseCase(repository),
            getRecipeById = GetRecipeByIdUseCase(repository),
            deleteRecipe = DeleteRecipeUseCase(repository),
            updateRecipe = UpdateRecipeUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCollectionUseCases(repository: CollectionRepository): CollectionUseCases {
        return CollectionUseCases(
            createCollection = CreateCollectionUseCase(repository),
            getCollections = GetCollectionsUseCase(repository),
            getCollectionsWithRecipes = GetCollectionsWithRecipesUseCase(repository),
            addRecipeToCollection = AddRecipeToCollectionUseCase(repository),
            removeRecipeFromCollection = RemoveRecipeFromCollectionUseCase(repository),
            deleteCollection = DeleteCollectionUseCase(repository),
            updateCollection = UpdateCollectionUseCase(repository)
        )
    }
}
