package com.vision19.mathplus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlin.reflect.KClass

//@Composable
//@Preview
//fun MultipleChoicesPreview(choices: List<Int> = listOf(1, 2, 3, 4)) {
//    MultipleChoices(choices = choices) { selectedChoice ->
//        // Handle button click action if needed
//    }
//}

@Composable
fun MultipleChoices(choices: List<Int>, onClick: (Int) -> Unit) {
    val buttonModifier = Modifier
        .size(150.dp)
        .padding(10.dp)
        .clip(RoundedCornerShape(0.1.dp))

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(450.dp)
    ) {
        // Top row
        Button(
            onClick = { onClick(choices[0]) },
            shape = RoundedCornerShape(10.dp),
            modifier = buttonModifier.then(Modifier.align(Alignment.CenterStart))
        ) {
            Text(text = "${choices[0]}",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Button(
            onClick = { onClick(choices[1]) },
            shape = RoundedCornerShape(10.dp),
            modifier = buttonModifier.then(Modifier.align(Alignment.CenterEnd))
        ) {
            Text(text = "${choices[1]}",
                style = MaterialTheme.typography.labelMedium
            )
        }

        // Bottom row
        Button(
            onClick = { onClick(choices[2]) },
            shape = RoundedCornerShape(10.dp),
            modifier = buttonModifier.then(Modifier.align(Alignment.BottomStart))
        ) {
            Text(text = "${choices[2]}",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Button(
            onClick = { onClick(choices[3]) },
            shape = RoundedCornerShape(10.dp),
            modifier = buttonModifier.then(Modifier.align(Alignment.BottomEnd))
        ) {
            Text(text = "${choices[3]}",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}