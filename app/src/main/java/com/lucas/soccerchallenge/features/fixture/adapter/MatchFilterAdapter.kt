package com.lucas.soccerchallenge.features.fixture.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.Competition
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.features.home.filter.MatchFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class MatchFilterAdapter<T : MatchFilterViewHolder> constructor(
    private val matchFilter: MatchFilter
) : RecyclerView.Adapter<T>() {

    lateinit var onMatchClick: (Match) -> Unit

    suspend fun setFilter(newFilters: Set<Competition>) {
        matchFilter.filters = newFilters
        val newFilteredMatches = matchFilter.getFilteredMatches(matchFilter.matches)
        val diffCallback = MatchDiffCallBack(matchFilter.filteredMatches, newFilteredMatches)
        val diffResult = withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(diffCallback)
        }
        matchFilter.setFilteredMatches(newFilteredMatches)
        diffResult.dispatchUpdatesTo(this)
    }

    suspend fun setMatches(newMatches: List<Match>) {
        val newFilteredMatches = matchFilter.getFilteredMatches(newMatches)
        matchFilter.matches = newMatches
        val diffCallback = MatchDiffCallBack(matchFilter.filteredMatches, newFilteredMatches)
        val diffResult = withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(diffCallback)
        }
        matchFilter.setFilteredMatches(newFilteredMatches)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        matchFilter.filteredMatches.getOrNull(position)?.let { match ->
            holder.bind(
                matchFilter.filteredMatches.getOrNull(position - 1),
                match
            )
        }
    }

    override fun getItemCount(): Int {
        return matchFilter.filteredMatches.size
    }

}
