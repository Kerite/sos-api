package xyz.keriteal.sosbk.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosbk.repository.ApplicationRepository

@Service
class AuthService @Autowired constructor(
    private val applicationRepository: ApplicationRepository
) {

}