package org.bruwave.abacusflow.usecase.user.service.impl

import org.bruwave.abacusflow.commons.Sex
import org.bruwave.abacusflow.db.user.UserRepository
import org.bruwave.abacusflow.usecase.user.CreateUserInputTO
import org.bruwave.abacusflow.usecase.user.UpdateUserInputTO
import org.bruwave.abacusflow.usecase.user.UserTO
import org.bruwave.abacusflow.usecase.user.mapper.toTO
import org.bruwave.abacusflow.usecase.user.service.UserCommandService
import org.bruwave.abacusflow.user.User
import org.bruwave.abacusflow.user.UserPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandServiceImpl(
    private val userRepository: UserRepository,
    private val userPasswordEncoder: UserPasswordEncoder,
) : UserCommandService {
    override fun createUser(input: CreateUserInputTO): UserTO {
        val newUser = User(name = input.name)
        newUser.updateProfile(
            newSex = input.sex?.let { Sex.valueOf(it) },
            newAge = input.age,
            newNick = input.nick,
        )
        newUser.resetPassword(userPasswordEncoder)
        return userRepository.save(newUser).toTO()
    }

    override fun updateUser(
        id: Long,
        input: UpdateUserInputTO,
    ): UserTO {
        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
        user.updateProfile(
            newSex = input.sex?.let { Sex.valueOf(it) },
            newAge = input.age,
            newNick = input.nick,
        )
        return userRepository.save(user).toTO()
    }

    override fun deleteUser(id: Long): UserTO {
        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
        userRepository.delete(user)
        return user.toTO()
    }
}
