package com.haroof.coin_detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haroof.common.extension.roundDecimal
import com.haroof.data.model.DetailedCoin
import com.haroof.designsystem.theme.green

@Composable
internal fun HeaderSection(coin: DetailedCoin) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = "$${coin.currentPrice}",
      style = MaterialTheme.typography.h5
    )
    Spacer(Modifier.width(12.dp))
    Surface(
      shape = CircleShape,
      color = green.copy(alpha = 0.2f),
      contentColor = green,
    ) {
      Text(
        text = "${coin.priceChangePercentage24h.roundDecimal(2)}%",
        style = MaterialTheme.typography.body2.copy(color = green),
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 4.dp),
      )
    }
  }
}
