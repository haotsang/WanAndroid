package com.haotsang.wanandroid.model.api

class ApiException(var code: Int, override var message: String) : RuntimeException()