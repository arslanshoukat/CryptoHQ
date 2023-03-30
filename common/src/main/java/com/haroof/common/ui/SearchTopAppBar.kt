package com.haroof.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.black100
import com.haroof.designsystem.theme.black800
import com.haroof.designsystem.theme.black900
import com.haroof.common.R as commonR

@Composable
fun SearchTopAppBar(
  value: String,
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp),
  onValueChange: (String) -> Unit = {},
) {
  val color = black100
  val mediumEmphasisColor = color.copy(ContentAlpha.medium)
  CustomShapeAppBar(
    contentPadding = PaddingValues(16.dp),
    shape = shape,
    modifier = modifier.fillMaxWidth(),
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      textStyle = MaterialTheme.typography.body1.copy(color = color),
      placeholder = {
        Text(
          text = stringResource(commonR.string.search_by_name),
          style = MaterialTheme.typography.body1.copy(color = mediumEmphasisColor)
        )
      },
      leadingIcon = {
        Icon(
          painter = painterResource(id = commonR.drawable.sharp_search_24),
          contentDescription = null,
        )
      },
      trailingIcon = {
        if (value.isNotEmpty()) {
          Icon(
            painter = painterResource(id = commonR.drawable.sharp_clear_24),
            contentDescription = null,
            modifier = Modifier.clickable { onValueChange("") }
          )
        }
      },
      keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
      singleLine = true,
      colors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = black800,
        focusedBorderColor = black900,
        unfocusedBorderColor = black900,
        textColor = color,
        cursorColor = color,
        leadingIconColor = mediumEmphasisColor,
        trailingIconColor = mediumEmphasisColor,
      ),
      shape = CircleShape,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun SearchTopAppBarEmptyPreview() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SearchTopAppBar(value = "")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SearchTopAppBarWithTextPreview() {
  CryptoHqTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SearchTopAppBar(value = "bitcoin")
    }
  }
}