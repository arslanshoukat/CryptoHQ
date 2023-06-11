package com.haroof.settings.tos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.haroof.settings.tos.util.Constants

@Composable
fun TermsOfServiceRoute(isTermsOfService: Boolean) {
  TermsOfServiceScreen(
    htmlData = if (isTermsOfService) Constants.TERMS_OF_SERVICE else Constants.PRIVACY_POLICY
  )
}

@Composable
internal fun TermsOfServiceScreen(htmlData: String) {
  Box(modifier = Modifier.fillMaxSize()) {
    WebView(
      state = rememberWebViewStateWithHTMLData(data = htmlData),
      modifier = Modifier.fillMaxHeight()
    )
  }
}