package com.vladimirbaklan.database

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import org.jooq.{DSLContext, SQLDialect}
import org.jooq.impl.DSL

object DatabaseWiring {
  private val DatabaseConfig = ConfigFactory.load().getConfig("db")
  lazy val Jooq: DSLContext = DSL.using(datasource(), SQLDialect.POSTGRES)

  private def datasource(): HikariDataSource = {
    val config = DatabaseConfig.getConfig("default")
    val user = config.getString("username")
    val password = config.getString("password")
    val url = config.getString("url")
    val maxConnections = config.getInt("maxConnections")
    val leakDetectionThresholdMs = config.getInt("leakDetectionThresholdMs")

    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(url)
    hikariConfig.setUsername(user)
    hikariConfig.setPassword(password)
    hikariConfig.setMaximumPoolSize(maxConnections)
    hikariConfig.setLeakDetectionThreshold(leakDetectionThresholdMs)
    new HikariDataSource(hikariConfig)
  }
}
