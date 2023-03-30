package com.haroof.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * AppBar that lets user set custom shape.
 * */
@Composable
fun CustomShapeAppBar(
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  elevation: Dp = AppBarDefaults.TopAppBarElevation,
  contentPadding: PaddingValues = AppBarDefaults.ContentPadding,
  content: @Composable RowScope.() -> Unit
) {
  Surface(
    color = backgroundColor,
    contentColor = contentColor,
    elevation = elevation,
    shape = shape,
    modifier = modifier
  ) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
      Row(
        Modifier
          .fillMaxWidth()
          .padding(contentPadding)
          .height(AppBarHeight),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = content
      )
    }
  }
}

private val AppBarHeight = 56.dp
