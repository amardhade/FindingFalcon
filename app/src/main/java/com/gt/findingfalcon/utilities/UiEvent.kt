package com.gt.findingfalcon.utilities

sealed class UiEvent(route: String? = null) {

    data class NavigateTo(val routeToNav: String?) : UiEvent(route = routeToNav)

    object NavigateUp : UiEvent()

    data class ShowSnackBar(val msg: UiText) : UiEvent()
}