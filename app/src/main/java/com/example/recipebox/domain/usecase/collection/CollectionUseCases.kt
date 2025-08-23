package com.example.recipebox.domain.usecase.collection

data class CollectionUseCases(
    val createCollection: CreateCollectionUseCase,
    val getCollections: GetCollectionsUseCase,
    val getCollectionsWithRecipes: GetCollectionsWithRecipesUseCase,
    val addRecipeToCollection: AddRecipeToCollectionUseCase,
    val removeRecipeFromCollection: RemoveRecipeFromCollectionUseCase,
    val deleteCollection: DeleteCollectionUseCase,
    val updateCollection: UpdateCollectionUseCase
)