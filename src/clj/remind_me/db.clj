(ns remind-me.db
  (:require [hugsql.core :as hugsql]))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "SQLite"
              :subname "/home/not-much-io/Projects/remind-me/resources/private/remind-me.db"})

(hugsql/def-db-fns "remind_me/queries/queries.sql")