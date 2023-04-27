package com.haroof.converter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.converter.ConverterUiState.Error
import com.haroof.converter.ConverterUiState.Loading
import com.haroof.converter.ConverterUiState.Success
import com.haroof.converter.R.string
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.CurrencySampleData
import com.haroof.common.R as commonR

@Composable
fun ConverterRoute(
  viewModel: ConverterViewModel = hiltViewModel(),
  onNavigateToSelectCurrency: (isSourceCurrency: Boolean) -> Unit = {}
) {
  val uiState by viewModel.uiState.collectAsState()

  ConverterScreen(
    uiState = uiState,
    onNavigateToSelectCurrency = onNavigateToSelectCurrency
  )
}

@Composable
internal fun ConverterScreen(
  uiState: ConverterUiState,
  onNavigateToSelectCurrency: (isSourceCurrency: Boolean) -> Unit = {}
) {
  Column(modifier = Modifier.fillMaxSize()) {
    TopAppBar(
      contentPadding = PaddingValues(16.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = stringResource(string.converter),
        style = MaterialTheme.typography.h6,
      )
    }

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

      is Error -> {
        ErrorMessageWithIcon()
      }

      is Success -> {
        val contentDesc = stringResource(id = string.converter_content_desc)
        ConstraintLayout(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .semantics { contentDescription = contentDesc }
        ) {
          val (fromCurrency, divider, swapIcon, toCurrency) = createRefs()

          SourceCurrencyCard(
            currency = uiState.from,
            onValueChanged = {},
            onClick = { onNavigateToSelectCurrency(true) },
            modifier = Modifier.constrainAs(fromCurrency) {
              top.linkTo(parent.top, 32.dp)
              start.linkTo(parent.start)
            }
          )

          Divider(
            color = Color.LightGray,
            modifier = Modifier.constrainAs(divider) {
              top.linkTo(fromCurrency.bottom, 24.dp)
              start.linkTo(parent.start)
            }
          )

          Card(
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = contentColorFor(MaterialTheme.colors.primary),
            elevation = 2.dp,
            modifier = Modifier.constrainAs(swapIcon) {
              centerVerticallyTo(divider)
              centerHorizontallyTo(parent)
            }
          ) {
            Icon(
              painter = painterResource(commonR.drawable.sharp_swap_vert_24),
              contentDescription = null,
              modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
            )
          }


          CurrencyCard(
            currency = uiState.to,
            onClick = { onNavigateToSelectCurrency(false) },
            modifier = Modifier.constrainAs(toCurrency) {
              top.linkTo(divider.bottom, 24.dp)
              start.linkTo(parent.start)
            }
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ConverterScreenPreview_Loading() {
  CryptoHqTheme {
    ConverterScreen(uiState = Loading)
  }
}

@Preview(showBackground = true)
@Composable
private fun ConverterScreenPreview_Success() {
  CryptoHqTheme {
    ConverterScreen(
      uiState = Success(
        from = CurrencySampleData.BTC,
        to = CurrencySampleData.USD,
      )
    )
  }
}
