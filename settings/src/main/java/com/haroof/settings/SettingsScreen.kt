package com.haroof.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haroof.common.R.drawable
import com.haroof.common.ui.CustomShapeAppBar
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.black200
import com.haroof.designsystem.theme.iconLightBlack
import com.haroof.designsystem.theme.textLightBlack
import com.haroof.domain.sample_data.CurrencySampleData
import com.haroof.settings.R.string
import com.haroof.settings.SettingsUiState.Error
import com.haroof.settings.SettingsUiState.Loading
import com.haroof.settings.SettingsUiState.Success
import com.haroof.common.R as commonR

@Composable
fun SettingsRoute(
  viewModel: SettingsViewModel = hiltViewModel(),
  onNavigateToCurrencySettings: () -> Unit = {},
  onNavigateToAbout: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()

  SettingsScreen(
    uiState = uiState,
    onOpenCurrencySettings = onNavigateToCurrencySettings,
    onNavigateToAbout = onNavigateToAbout,
  )
}

@Composable
fun SettingsScreen(
  uiState: SettingsUiState,
  onOpenCurrencySettings: () -> Unit = {},
  onNavigateToAbout: () -> Unit = {},
) {
  Column(
    modifier = Modifier.fillMaxSize()
  ) {
    CustomShapeAppBar(
      contentPadding = PaddingValues(16.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        text = "Settings",
        style = MaterialTheme.typography.h6
      )
    }

    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(commonR.string.loading_indicator)
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          CircularProgressIndicator(
            modifier = Modifier.semantics { contentDescription = contentDesc }
          )
        }
      }

      is Error -> {
        ErrorMessageWithIcon()
      }

      is Success -> {
        Spacer(modifier = Modifier.height(16.dp))

        SettingsItem(
          title = string.currency,
          value = uiState.currency,
          icon = drawable.sharp_attach_money_24,
          onItemClicked = onOpenCurrencySettings,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingsItem(
          title = string.terms_of_service,
          icon = drawable.sharp_menu_book_24,
          onItemClicked = onNavigateToAbout,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingsItem(
          title = string.about,
          icon = drawable.ic_about,
          onItemClicked = onNavigateToAbout,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
          text = "Version: v${BuildConfig.VERSION_NAME}",
          color = textLightBlack,
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Composable
private fun SettingsItem(
  @StringRes title: Int,
  value: String = "",
  @DrawableRes icon: Int,
  onItemClicked: () -> Unit = {},
  tint: Color = iconLightBlack,
) {
  Card(
    elevation = 2.dp,
    modifier = Modifier.padding(horizontal = 16.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClicked() }
        .padding(16.dp)
    ) {
      Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        tint = tint
      )

      Spacer(modifier = Modifier.width(16.dp))

      Text(
        text = stringResource(title),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.weight(1f)
      )

      if (value.isNotBlank()) {
        Text(
          text = value.uppercase(),
          style = MaterialTheme.typography.body2,
          textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.width(16.dp))
      }

      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(28.dp)
          .background(color = black200, shape = CircleShape)
      ) {
        Icon(
          painter = painterResource(id = drawable.sharp_chevron_right_24),
          contentDescription = null,
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview_Loading() {
  CryptoHqTheme {
    SettingsScreen(uiState = Loading)
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview_Success() {
  CryptoHqTheme {
    SettingsScreen(
      uiState = Success(
        currency = CurrencySampleData.AED.code,
      )
    )
  }
}
