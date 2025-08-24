package com.example.recipebox.domain.di

import com.example.recipebox.domain.repository.CollectionRepository
import com.example.recipebox.domain.repository.ImageRepository
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
import com.example.recipebox.domain.usecase.collection.GetCollectionWithRecipesFlow
import com.example.recipebox.domain.usecase.collection.GetCollectionWithRecipesUseCase
import com.example.recipebox.domain.usecase.collection.GetCollectionsUseCase
import com.example.recipebox.domain.usecase.collection.GetCollectionsWithRecipesUseCase
import com.example.recipebox.domain.usecase.collection.RemoveRecipeFromCollectionUseCase
import com.example.recipebox.domain.usecase.collection.UpdateCollectionUseCase
import com.example.recipebox.domain.usecase.recipe.DeleteImageUseCase
import com.example.recipebox.domain.usecase.recipe.GetImageUriUseCase
import com.example.recipebox.domain.usecase.recipe.ImageUseCases
import com.example.recipebox.domain.usecase.recipe.SaveImageToInternalStorageUseCase
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
    fun provideRecipeUseCases(
        addRecipe: AddRecipeUseCase,
        getRecipes: GetRecipesUseCase,
        searchRecipes: SearchRecipesUseCase,
        getRecipeById: GetRecipeByIdUseCase,
        deleteRecipe: DeleteRecipeUseCase,
        updateRecipe: UpdateRecipeUseCase
    ): RecipeUseCases {
        return RecipeUseCases(
            addRecipe = addRecipe,
            getRecipes = getRecipes,
            searchRecipes = searchRecipes,
            getRecipeById = getRecipeById,
            deleteRecipe = deleteRecipe,
            updateRecipe = updateRecipe
        )
    }
    @Provides
    @Singleton
    fun provideCollectionUseCases(
        createCollection: CreateCollectionUseCase,
        deleteCollection: DeleteCollectionUseCase,
        updateCollection: UpdateCollectionUseCase,
        getCollections: GetCollectionsUseCase,
        getCollectionsWithRecipes: GetCollectionsWithRecipesUseCase,
        getCollectionWithRecipes: GetCollectionWithRecipesUseCase,
        addRecipeToCollection: AddRecipeToCollectionUseCase,
        removeRecipeFromCollection: RemoveRecipeFromCollectionUseCase,
        getCollectionWithRecipesFlow: GetCollectionWithRecipesFlow
    ): CollectionUseCases {
        return CollectionUseCases(
            createCollection = createCollection,
            deleteCollection = deleteCollection,
            updateCollection = updateCollection,
            getCollections = getCollections,
            getCollectionsWithRecipes = getCollectionsWithRecipes,
            getCollectionWithRecipes = getCollectionWithRecipes,
            addRecipeToCollection = addRecipeToCollection,
            removeRecipeFromCollection = removeRecipeFromCollection,
            getCollectionWithRecipesFlow = getCollectionWithRecipesFlow
        )
    }

    @Provides
    @Singleton
    fun provideImageUseCases(repository: ImageRepository): ImageUseCases {
        return ImageUseCases(
            saveImageToInternalStorage = SaveImageToInternalStorageUseCase(repository),
            deleteImage = DeleteImageUseCase(repository),
            getImageUri = GetImageUriUseCase(repository)
        )
    }

}