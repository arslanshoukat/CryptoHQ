package com.haroof.home

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.haroof.home.HomeUiState.Loading

@Composable
fun HomeScreen(uiState: HomeUiState) {
  when (uiState) {
    Loading -> {
      val loadingStringRes = stringResource(R.string.loading_indicator)
      CircularProgressIndicator(
        modifier = Modifier.semantics { contentDescription = loadingStringRes }
      )
    }
  }
}
