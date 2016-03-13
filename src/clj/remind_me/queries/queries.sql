-- :name create-reminders-table :!
CREATE TABLE reminders (
  id INTEGER PRIMARY KEY,
  name TEXT
);

-- :name insert-reminder :! :n
INSERT INTO reminders (name)
VALUES (:name);

-- :name delete-reminder-with-id :! :n
DELETE FROM reminders WHERE id = :id;

-- :name get-reminders
SELECT * FROM reminders;