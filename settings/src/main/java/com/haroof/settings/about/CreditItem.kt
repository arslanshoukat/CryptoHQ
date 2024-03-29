package com.haroof.settings.about

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.settings.R.drawable
import com.haroof.settings.R.string

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreditItem(
  @DrawableRes iconResId: Int,
  @StringRes titleResId: Int,
  @StringRes creditTextResId: Int,
  link: String,
) {
  val context = LocalContext.current
  Card(
    modifier = Modifier.fillMaxWidth(),
    onClick = { openLink(context, link) }
  ) {
    ConstraintLayout(modifier = Modifier.padding(16.dp)) {
      val (icon, title, text) = createRefs()

      Image(
        painter = painterResource(id = iconResId),
        contentDescription = null,
        modifier = Modifier
          .size(64.dp)
          .constrainAs(icon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
          }
      )

      Text(
        text = stringResource(titleResId),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.constrainAs(title) {
          top.linkTo(icon.top)
          start.linkTo(icon.end, 8.dp)
        }
      )

      Text(
        text = stringResource(creditTextResId),
        style = MaterialTheme.typography.body2,
        modifier = Modifier
          .constrainAs(text) {
            top.linkTo(title.bottom, 2.dp)
            linkTo(start = title.start, end = parent.end)
            width = Dimension.fillToConstraints
          }
      )
    }
  }
}

private fun openLink(context: Context, url: String) {
  try {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  } catch (e: ActivityNotFoundException) {
    Log.e("Credit Item", e.localizedMessage, e)
  }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
  CryptoHqTheme {
    CreditItem(
      iconResId = drawable.coingecko_logo,
      titleResId = string.coingecko,
      creditTextResId = string.coingecko_credits,
      link = stringResource(string.coingecko_link),
    )
  }
}