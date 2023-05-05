package com.haroof.converter.select_currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haroof.converter.R.string
import com.haroof.converter.select_currency.SelectCurrencyUiState.Loading
import com.haroof.converter.select_currency.SelectCurrencyUiState.Success
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.CurrencySampleData
import com.haroof.common.R as commonR

@Composable
internal fun SelectCurrencyRoute(
  viewModel: SelectCurrencyViewModel = hiltViewModel(),
  onBackPressed: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()

  SelectCurrencyScreen(
    uiState = uiState,
    onBackPressed = onBackPressed,
    onCurrencySelected = viewModel::selectCurrency,
  )
}

@Composable
internal fun SelectCurrencyScreen(
  uiState: SelectCurrencyUiState,
  onBackPressed: () -> Unit = {},
  onCurrencySelected: (code: String) -> Unit = {}
) {
  Column(Modifier.fillMaxSize()) {
    TopAppBar(
      title = {
        Text(text = stringResource(string.select_currency))
      },
      navigationIcon = {
        IconButton(onClick = onBackPressed) {
          Icon(
            painter = painterResource(id = commonR.drawable.sharp_arrow_back_24),
            contentDescription = null
          )
        }
      },
      modifier = Modifier.fillMaxWidth()
    )

    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(id = commonR.string.loading_indicator)
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          CircularProgressIndicator(
            modifier = Modifier
              .align(Alignment.Center)
              .semantics { contentDescription = contentDesc }
          )
        }
      }

      is Success -> {
        val contentDesc = stringResource(string.selectable_currencies_content_desc)

        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.surface)
            .semantics { contentDescription = contentDesc }
        ) {
          itemsIndexed(
            items = uiState.selectableCurrencies,
            key = { _, selectableCurrency -> selectableCurrency.code }
          ) { index, selectableCurrency ->
            SelectableCurrencyCard(
              currency = selectableCurrency,
              selected = selectableCurrency.code == uiState.selectedCurrencyCode,
              onCurrencySelected = onCurrencySelected,
            )

            if (index < uiState.selectableCurrencies.size - 1) {
              Divider(modifier = Modifier.fillMaxWidth())
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SelectCurrencyScreenPreview_Loading() {
  CryptoHqTheme {
    SelectCurrencyScreen(uiState = Loading)
  }
}

@Preview(showBackground = true)
@Composable
private fun SelectCurrencyScreenPreview_Success() {
  CryptoHqTheme {
    SelectCurrencyScreen(
      uiState = Success(
        selectableCurrencies = CurrencySampleData.LIST,
        selectedCurrencyCode = CurrencySampleData.USD.code,
      )
    )
  }
}