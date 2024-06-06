package com.example.storyappdicoding.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyappdicoding.data.local.pref.UserPreference
import com.example.storyappdicoding.data.remote.response.StoryItem
import com.example.storyappdicoding.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import java.lang.Exception

class PagingSource(private val apiService: ApiService, private val userPreference: UserPreference) : PagingSource<Int, StoryItem>() {


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> {
        return try  {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = userPreference.getToken().first()
            val response = apiService.listStory("Bearer $token", position, params.loadSize, 1)

            LoadResult.Page(
                data = response.listStory.map {
                    StoryItem(it.id, it.name, it.photoUrl, it.createdAt, it.description, it.lat, it.lon)
                },
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (response.listStory.isEmpty()) null else position +1
            )
        } catch (exc: Exception) {
            return LoadResult.Error(exc)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



}