package com.haroof.common.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haroof.common.R.drawable
import com.haroof.common.R.string

@Composable
fun ErrorMessageWithIcon(
  @DrawableRes errorIconResId: Int = drawable.ic_error_alert,
  @StringRes errorMessageResId: Int = string.error_message_data_fetch,
  contentDesc: String = stringResource(string.error_message),
  modifier: Modifier = Modifier
) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
      .semantics { contentDescription = contentDesc }
  ) {
    Image(
      painter = painterResource(id = errorIconResId),
      contentDescription = stringResource(string.error_icon),
      modifier = Modifier.size(128.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = stringResource(id = errorMessageResId),
      style = MaterialTheme.typography.h5,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}