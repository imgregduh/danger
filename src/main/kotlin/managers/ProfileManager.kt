package com.doubleu.danger.managers

import com.doubleu.danger.controllers.presenters.UserProfilePresenter
import com.doubleu.danger.entities.ProfileEntity
import com.doubleu.danger.repositories.ProfilesRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileManager {

    @Autowired
    private lateinit var profileRepo: ProfilesRepository

    fun getProfile(id: String): Optional<ProfileEntity> {
        val profile = profileRepo.findById(ObjectId(id))
        return profile
    }

    fun getProfiles(): List<ProfileEntity> {
        val profiles = profileRepo.findAll()
        return profiles
    }

    fun createProfile(profile: UserProfilePresenter): ProfileEntity {
        val profile = ProfileEntity(null, profile.email, profile.firstName, profile.lastName)
        val newProfile = profileRepo.insert(profile)
        return newProfile
    }
}