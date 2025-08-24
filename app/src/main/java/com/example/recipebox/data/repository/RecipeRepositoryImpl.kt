package com.example.recipebox.data.repository

import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.data.relations.RecipeWithCollections
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.model.RecipeWithCollectionsModel
import com.example.recipebox.domain.model.mappers.toData
import com.example.recipebox.domain.model.mappers.toDomain
import com.example.recipebox.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton




@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun insertRecipe(recipe: RecipeModel): Long =
        recipeDao.insert(recipe.toData())

    override suspend fun updateRecipe(recipe: RecipeModel) =
        recipeDao.update(recipe.toData())

    override suspend fun deleteRecipe(recipe: RecipeModel) =
        recipeDao.delete(recipe.toData())

    override suspend fun getRecipeById(id: Int): RecipeModel? =
        recipeDao.getById(id)?.toDomain()

    override fun getAllRecipes(): Flow<List<RecipeModel>> =
        recipeDao.getAllRecipes().map { recipes ->
            recipes.map { it.toDomain() }
        }

    override fun searchRecipes(query: String?, tags: List<String>?): Flow<List<RecipeModel>> {
        val tagsJson = tags?.takeIf { it.isNotEmpty() }?.joinToString(",") { "\"$it\"" }?.let { "[$it]" }
        return recipeDao.searchRecipes(query, tagsJson).map { recipes ->
            recipes.map { it.toDomain() }
        }
    }

    override suspend fun getRecipeWithCollections(recipeId: Int): RecipeWithCollectionsModel? =
        recipeDao.getRecipeWithCollections(recipeId)?.toDomain()
}