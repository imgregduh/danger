package com.doubleu.danger

import com.doubleu.danger.controllers.presenters.UserProfilePresenter
import com.doubleu.danger.entities.ProfileEntity
import com.doubleu.danger.managers.ProfileManager
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserProfileController(private val profileManager: ProfileManager) {

    @GetMapping(
            "/profile/{id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun getProfileById(@PathVariable("id") id: String): ResponseEntity<ProfileEntity> {
        val profile = profileManager.getProfile(id)
        if (profile.isPresent) {
            return ResponseEntity.ok(profile.get())
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

    }

    @GetMapping(
            "/profiles",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    @ResponseBody
    fun getProfiles(): List<ProfileEntity> {
        val profiles = profileManager.getProfiles()
        return profiles
    }

    @PostMapping(
            "/profile",
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    @ResponseBody
    fun createProfile(@RequestBody presenter: UserProfilePresenter): ProfileEntity {
        return profileManager.createProfile(presenter)
    }
}