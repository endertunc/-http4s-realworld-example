package com.real.world.http4s.repository

import com.real.world.http4s.base.ServicesAndRepos
import com.real.world.http4s.generators.UserGenerator
import org.scalatest.flatspec.AnyFlatSpec

import cats.data.NonEmptyList
import cats.effect.IO

import doobie.scalatest.IOChecker

class UserRepositorySpec extends AnyFlatSpec with IOChecker with ServicesAndRepos {

  private val user                               = UserGenerator.generateUser
  override def transactor: doobie.Transactor[IO] = xa

  "FollowersStatements" should "compile" in {
    check(UserStatement.saveUser[IO](user).unsafeRunSync)
    check(UserStatement.findUserByUserId(user.id))
    check(UserStatement.findUserByUsername(user.username))
    check(UserStatement.updateUser[IO](user).unsafeRunSync())
    check(UserStatement.findUserByEmail(user.email))
    check(UserStatement.findProfilesByUserId(NonEmptyList.of(user.id), user.id))
    check(UserStatement.findUsersByUserId(NonEmptyList.of(user.id)))
  }

}
