package com.exam.appetiserchallenge.core.modules

import com.exam.appetiserchallenge.data.service.ApiService
import org.koin.dsl.module


/**
 * Dependency module for [ApiService]
 * */
val serviceModule = module {
    single { ApiService() }
}
