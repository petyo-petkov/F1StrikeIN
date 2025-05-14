package org.example.project.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initiKoin(config: KoinAppDeclaration? = null) {

    startKoin {
        config?.invoke(this)
        modules(commonModule)
    }
}