package com.doubleu.danger.repositories

import com.doubleu.danger.entities.ProfileEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface ProfilesRepository : MongoRepository<ProfileEntity, ObjectId>