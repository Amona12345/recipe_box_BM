package com.example.recipebox.domain.usecase.collection

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class CollectionUseCases @Inject constructor(
    val createCollection: CreateCollectionUseCase,
    val deleteCollection: DeleteCollectionUseCase,
    val updateCollection: UpdateCollectionUseCase,
    val getCollections: GetCollectionsUseCase,
    val getCollectionsWithRecipes: GetCollectionsWithRecipesUseCase,
    val getCollectionWithRecipes: GetCollectionWithRecipesUseCase,
    val addRecipeToCollection: AddRecipeToCollectionUseCase,
    val removeRecipeFromCollection: RemoveRecipeFromCollectionUseCase,
    val getCollectionWithRecipesFlow:GetCollectionWithRecipesFlow
)