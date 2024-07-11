package com.gt.findingfalcon.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gt.findingfalcon.MainActivityViewModel
import com.gt.findingfalcon.R
import com.gt.findingfalcon.utilities.LocalSpacing

@Preview
@Composable
fun FindingFalconAppBar(
    modifier: Modifier = Modifier,
    onActionPerformed: (actionType: AppToolbarType) -> Unit = {},
    mainActivityViewModel: MainActivityViewModel = hiltViewModel() as MainActivityViewModel
) {
    val localSpacing = LocalSpacing.current
    AnimatedVisibility(
        visible = mainActivityViewModel.shouldDisplayTopBar(),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
        AppBar(mainActivityViewModel, onActionPerformed)
    }


}

@Composable
fun AppBar(
    mainActivityViewModel: MainActivityViewModel,
    onActionPerformed: (actionType: AppToolbarType) -> Unit = {}
) {
    val localSpacing = LocalSpacing.current
    TopAppBar(
        modifier = Modifier,
        backgroundColor = Color.LightGray,
        contentColor = Color.White,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(localSpacing.Default),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    modifier = Modifier
                        .padding(localSpacing.spaceExtraSmall),
                    text = stringResource(id = R.string.finding_falcone_title),
                    color = Color.Blue
                )
            }

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onActionPerformed(mainActivityViewModel.toolBarType())
                }, modifier = Modifier
                    .padding(localSpacing.spaceExtraSmall)
            ) {
                when (mainActivityViewModel.toolBarType()) {
                    AppToolbarType.BACK -> {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }

                    AppToolbarType.HAMBURGER -> {}
                    else -> {}

                }
            }
        }
    )
}

enum class AppToolbarType {
    BACK, HAMBURGER
}

data class TopAppBar(
    var shouldDisplayAppBar: Boolean = false,
    var toolbarType: AppToolbarType = AppToolbarType.BACK
)