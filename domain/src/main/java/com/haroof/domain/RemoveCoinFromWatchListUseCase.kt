package com.haroof.domain

import com.haroof.data.repository.WatchListRepository
import javax.inject.Inject

class RemoveCoinFromWatchListUseCase @Inject constructor(
  private val watchListRepository: WatchListRepository,
) {

  suspend operator fun invoke(coinId: String) {
    return watchListRepository.removeFromWatchList(coinId = coinId)
  }
}