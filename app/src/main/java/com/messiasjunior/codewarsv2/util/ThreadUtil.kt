package com.messiasjunior.codewarsv2.util

import java.util.concurrent.Executors

val BACKGROUND_EXECUTOR = Executors.newSingleThreadExecutor()!!

fun runOnBackground(runnable: () -> Unit) {
    BACKGROUND_EXECUTOR.execute(runnable)
}
