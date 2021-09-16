package com.example.mvvmnewsapp.ui.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error <T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()

}

/*
sealed : faghat class haei k too in class hastan mitoonann az in Resource ers bebaran yani Success , Error , Loading.
------------------
age Success ro seda bezanim faghat ye deta b ma barmigardone yani hamon responsemon ye jooraei
age Error seda zade she ham ye message i b onvane ekhtar b ma  mide ham mitoone ye data ei ham  bargardoone k in hamishegi nis bara hamin injoori neveshtimesh :  data: T? = null

*/