package com.carlosdiestro.brokeless.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButton
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButtonResource
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize

@Composable
fun BrokelessKeyboard(
    modifier: Modifier = Modifier,
    onKeyClicked: (String) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("1"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("1")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("2"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("2")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("3"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("3")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("4"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("4")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("5"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("5")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("6"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("6")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("7"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("7")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("8"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("8")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("9"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("9")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("."),
                containerColor = MaterialTheme.colorScheme.surface,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked(".")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.TextResource("0"),
                containerColor = MaterialTheme.colorScheme.secondary,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("0")
            }
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_delete),
                containerColor = MaterialTheme.colorScheme.surface,
                size = BrokelessIconContainerSize.Large
            ) {
                onKeyClicked("delete")
            }
        }
    }
}
