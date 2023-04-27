package com.haroof.converter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.haroof.converter.R.string
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.model.CurrencyUiModel
import com.haroof.domain.sample_data.CurrencySampleData
import com.haroof.common.R as commonR

@Composable
internal fun SourceCurrencyCard(
  currency: CurrencyUiModel,
  modifier: Modifier = Modifier,
  onValueChanged: (String) -> Unit = {},
  onClick: () -> Unit = {},
) {
  Card(modifier = modifier) {
    ConstraintLayout(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
      val (image, code, dropdownIcon, name, unit, value, calculateIcon) = createRefs()

      Image(
        painter = painterResource(id = currency.countryFlag),
        contentDescription = null,
        modifier = Modifier
          .size(32.dp)
          .constrainAs(image) {
            linkTo(top = code.top, bottom = name.bottom)
            start.linkTo(parent.start)
          }
      )

      Text(
        text = currency.code.uppercase(),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.constrainAs(code) {
          top.linkTo(parent.top)
          start.linkTo(image.end, 12.dp)
        }
      )
      Icon(
        painter = painterResource(id = commonR.drawable.sharp_arrow_drop_down_24),
        contentDescription = null,
        modifier = Modifier
          .size(24.dp)
          .constrainAs(dropdownIcon) {
            centerVerticallyTo(code)
            start.linkTo(code.end, 2.dp)
          }
      )
      Text(
        text = currency.name,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.constrainAs(name) {
          top.linkTo(code.bottom)
          start.linkTo(code.start)
        }
      )

      Text(
        text = currency.unit,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.constrainAs(unit) {
          top.linkTo(name.bottom, 4.dp)
          start.linkTo(code.start)
        }
      )

      val valueContentDescription = stringResource(string.source_currency_value_content_desc)
      BasicTextField(
        value = currency.currentValue.toString(),
        onValueChange = onValueChanged,
        textStyle = TextStyle.Default.copy(
          fontSize = MaterialTheme.typography.h4.fontSize,
          fontWeight = MaterialTheme.typography.h4.fontWeight,
          fontStyle = MaterialTheme.typography.h4.fontStyle,
          letterSpacing = MaterialTheme.typography.h4.letterSpacing,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
          keyboardType = KeyboardType.Number,
          imeAction = ImeAction.Done,
        ),
        modifier = Modifier
          .constrainAs(value) {
            baseline.linkTo(unit.baseline)
            linkTo(start = unit.end, end = parent.end, startMargin = 2.dp)
            width = Dimension.fillToConstraints
          }
          .semantics { contentDescription = valueContentDescription }
      )

      Icon(
        painter = painterResource(id = commonR.drawable.ic_calculator),
        contentDescription = stringResource(string.calculate_icon_content_desc),
        tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        modifier = Modifier
          .size(24.dp)
          .constrainAs(calculateIcon) {
            centerVerticallyTo(value)
            end.linkTo(parent.end)
          }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SourceCurrencyCardPreview() {
  CryptoHqTheme {
    Box {
      SourceCurrencyCard(CurrencySampleData.USD)
    }
  }
}