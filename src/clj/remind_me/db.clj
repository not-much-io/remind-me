(ns remind-me.db
  (:require [hugsql.core :as hugsql]))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "SQLite"
              :subname "C:\\Users\\KKoert\\Documents\\Sandbox\\remind-me\\resources\\private\\remind-me.db"})

(hugsql/def-db-fns "remind_me/queries/queries.sql")