package com.example.kotlinstory.controllers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest

@Controller
class LogoutController {
    @Autowired
    private val tokenStore: TokenStore? = null

    @RequestMapping(value = ["/oauth/revoke-token"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun logout(request: HttpServletRequest) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null) {
            val tokenValue = authHeader.replace("Bearer", "").trim { it <= ' ' }
            val accessToken = tokenStore!!.readAccessToken(tokenValue)
            tokenStore.removeAccessToken(accessToken)
        }
    }
}
