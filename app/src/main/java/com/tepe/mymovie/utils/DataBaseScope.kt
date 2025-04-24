package com.tepe.mymovie.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object DataBaseScope: CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.IO)