package com.haroof.coin_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.haroof.common.R.drawable
import com.haroof.common.R.string
import com.haroof.common.extension.roundDecimal
import com.haroof.data.model.DetailedCoin
import com.haroof.designsystem.theme.green

@Composable
internal fun HeaderSection(
  coin: DetailedCoin,
  isFavorite: Boolean,
  onBackPressed: () -> Unit = {},
  onToggleFavorite: (selected: Boolean) -> Unit = {},
  imageLoader: ImageLoader,
) {
  CoinDetailTopAppBar(
    coin = coin,
    isFavorite = isFavorite,
    onBackPressed = onBackPressed,
    onToggleFavorite = onToggleFavorite,
    imageLoader = imageLoader,
  )

  Spacer(modifier = Modifier.height(16.dp))
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Spacer(Modifier.width(16.dp))
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

@Composable
private fun CoinDetailTopAppBar(
  coin: DetailedCoin,
  isFavorite: Boolean,
  onBackPressed: () -> Unit = {},
  onToggleFavorite: (selected: Boolean) -> Unit = {},
  imageLoader: ImageLoader,
) {
  TopAppBar(
    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp)
  ) {
    IconButton(onClick = onBackPressed) {
      Icon(
        painter = painterResource(id = drawable.sharp_arrow_back_24),
        contentDescription = null
      )
    }

    AsyncImage(
      model = coin.imageUrl,
      imageLoader = imageLoader,
      error = painterResource(id = drawable.ic_default_coin),
      fallback = painterResource(id = drawable.ic_default_coin),
      contentScale = ContentScale.Crop,
      contentDescription = stringResource(string.coin_icon_content_desc),
      modifier = Modifier.size(40.dp)
    )

    Spacer(modifier = Modifier.width(16.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = coin.name,
        style = MaterialTheme.typography.h6
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = coin.symbol.uppercase(),
        style = MaterialTheme.typography.body1
      )
    }

    val favoriteIconContentDesc = stringResource(R.string.favorite_icon_content_desc)
    IconToggleButton(
      checked = isFavorite,
      onCheckedChange = onToggleFavorite,
      modifier = Modifier.semantics { contentDescription = favoriteIconContentDesc }
    ) {
      Icon(
        painter = painterResource(
          id = if (isFavorite) drawable.sharp_star_24 else drawable.outline_star_24
        ),
        contentDescription = null
      )
    }
  }
}
