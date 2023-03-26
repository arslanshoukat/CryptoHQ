package com.haroof.coin_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.haroof.common.R.drawable
import com.haroof.data.model.DetailedCoin

@Composable
internal fun AboutSection(
  coin: DetailedCoin,
  modifier: Modifier = Modifier
) {
  Text(
    text = "About",
    style = MaterialTheme.typography.h6
  )
  Spacer(modifier = Modifier.height(4.dp))

  Card(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      var expanded by remember {
        mutableStateOf(false)
      }

      if (coin.description.isNotBlank()) {
        Text(
          text = HtmlCompat.fromHtml(coin.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
          style = MaterialTheme.typography.body2,
          maxLines = if (expanded) Int.MAX_VALUE else 3,
          overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Icon(
          painter = painterResource(if (expanded) drawable.sharp_expand_less_24 else drawable.sharp_expand_more_24),
          contentDescription = "Expand icon",
          modifier = Modifier
            .clickable { expanded = !expanded }
            .size(32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
      }

      Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
      ) {
        val context = LocalContext.current
        IconButton(onClick = { openUrl(context, coin.links.facebook) }) {
          Icon(
            painter = painterResource(id = drawable.facebook),
            contentDescription = "Facebook icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
        IconButton(onClick = { openUrl(context, coin.links.twitter) }) {
          Icon(
            painter = painterResource(id = drawable.twitter),
            contentDescription = "Twitter icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
        IconButton(onClick = { openUrl(context, coin.links.reddit) }) {
          Icon(
            painter = painterResource(id = drawable.reddit),
            contentDescription = "Reddit icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }
  }
}

internal fun openUrl(context: Context, url: String) {
  try {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  } catch (e: ActivityNotFoundException) {
    // todo: show error if no activity found
    Log.e("Coin Detail", e.localizedMessage, e)
  }
}
