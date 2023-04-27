package com.haroof.converter.select_currency

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haroof.converter.R.string
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.model.CurrencyUiModel
import com.haroof.domain.sample_data.CurrencySampleData
import com.haroof.common.R as commonR

@Composable
internal fun SelectableCurrencyCard(
  currency: CurrencyUiModel,
  selected: Boolean,
  modifier: Modifier = Modifier,
  onCurrencySelected: (code: String) -> Unit = {}
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .clickable { onCurrencySelected(currency.code) }
      .padding(horizontal = 16.dp)
      .height(64.dp)
      .fillMaxWidth()
  ) {
    Image(
      painter = painterResource(id = currency.countryFlag),
      contentDescription = null,
      modifier = Modifier.size(32.dp)
    )

    Spacer(modifier = Modifier.width(16.dp))

    Text(
      text = currency.code.uppercase(),
      style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
    )

    Spacer(modifier = Modifier.width(16.dp))

    Text(
      text = "${currency.name} (${currency.unit})",
      style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
      maxLines = 2,
      modifier = Modifier.weight(1f)
    )

    if (selected) {
      Spacer(modifier = Modifier.width(8.dp))

      Icon(
        painter = painterResource(id = commonR.drawable.sharp_check_circle_24),
        contentDescription = stringResource(string.selected_icon_content_desc),
        modifier = Modifier.size(24.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SelectedCurrencyCard() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SelectableCurrencyCard(
        currency = CurrencySampleData.BTC,
        selected = true,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SelectedCurrencyCardWithLongName() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SelectableCurrencyCard(
        currency = CurrencySampleData.AED,
        selected = true,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun NotSelectedCurrencyCard() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SelectableCurrencyCard(
        currency = CurrencySampleData.USD,
        selected = false,
      )
    }
  }
}