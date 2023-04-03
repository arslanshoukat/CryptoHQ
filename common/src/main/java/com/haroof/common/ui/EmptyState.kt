package com.haroof.common.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haroof.common.R
import com.haroof.designsystem.theme.CryptoHqTheme

@Composable
fun EmptyState(
  modifier: Modifier = Modifier,
  @DrawableRes iconResId: Int = R.drawable.ic_favorite_folder,
  @StringRes titleResId: Int = R.string.empty_state_default_title,
  @StringRes subtitleResId: Int = R.string.empty_state_default_subtitle,
  @StringRes contentDescriptionResId: Int = R.string.empty_state_content_description,
) {
  val contentDesc = stringResource(id = contentDescriptionResId)
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .background(Color.White)
      .padding(16.dp)
      .semantics { contentDescription = contentDesc }
  ) {
    Image(
      painter = painterResource(id = iconResId),
      contentDescription = null,
      modifier = Modifier.size(192.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = stringResource(id = titleResId),
      style = MaterialTheme.typography.h6,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = stringResource(id = subtitleResId),
      style = MaterialTheme.typography.body2,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultEmptyStatePreview() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      EmptyState()
    }
  }
}