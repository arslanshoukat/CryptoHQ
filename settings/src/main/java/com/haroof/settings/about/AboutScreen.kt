package com.haroof.settings.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.settings.BuildConfig
import com.haroof.settings.R.drawable
import com.haroof.settings.R.string
import com.haroof.common.R as commonR

@Composable
fun AboutRoute(onBackPressed: () -> Unit = {}) {
  AboutScreen(onBackPressed = onBackPressed)
}

@Composable
internal fun AboutScreen(onBackPressed: () -> Unit = {}) {
  Column(modifier = Modifier.fillMaxSize()) {
    TopAppBar(
      title = {
        Text(text = stringResource(id = string.about))
      },
      navigationIcon = {
        IconButton(onClick = onBackPressed) {
          Icon(
            painter = painterResource(id = commonR.drawable.sharp_arrow_back_24),
            contentDescription = null
          )
        }
      }
    )

    val scrollState = rememberScrollState()
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ) {

      Text(
        text = stringResource(id = string.app_name) + " (v${BuildConfig.VERSION_NAME})",
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
      )

      Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
      ) {
        Text(
          text = stringResource(string.about_description),
          style = MaterialTheme.typography.body2,
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      //  Credits Section
      Text(
        text = "Credits",
        style = MaterialTheme.typography.h6,
      )
      Spacer(modifier = Modifier.height(8.dp))

      CreditItem(
        iconResId = drawable.coingecko_logo,
        titleResId = string.coingecko,
        creditTextResId = string.coingecko_credits
      )

      Spacer(modifier = Modifier.height(16.dp))

      CreditItem(
        iconResId = drawable.icons8_logo,
        titleResId = string.icons8,
        creditTextResId = string.icons8_credits
      )

      Spacer(modifier = Modifier.height(16.dp))

      CreditItem(
        iconResId = drawable.dribbble_logo,
        titleResId = string.empty_state_illustrator,
        creditTextResId = string.empty_state_illustrator_credits
      )

      Spacer(modifier = Modifier.height(16.dp))

      CreditItem(
        iconResId = drawable.github_logo,
        titleResId = string.vico,
        creditTextResId = string.vico_credits
      )

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
  CryptoHqTheme {
    AboutScreen()
  }
}