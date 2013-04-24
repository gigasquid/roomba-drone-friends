(ns roomba-drone-friends.drone-dance-beliefs
  (:require [clj-drone.core :refer :all]
            [clj-drone.goals :refer :all]
            [roomba-drone-friends.roomba-dance-beliefs :refer [dance-over]]))

(def cruising-altitude 1.5)

;;; Taking off
(def-belief-action ba-landed
  "I am landed"
  (fn [{:keys [control-state]}] (= control-state :landed))
  (fn [navdata] (drone :take-off)))

(def-belief-action ba-taking-off
  "I am taking off"
  (fn [{:keys [control-state]}] (= control-state :trans-takeoff))
  nil)

(def-goal g-take-off
  "I want to fly."
  (fn [{:keys [control-state]}] (= control-state :hovering))
  [ba-landed ba-taking-off])


;; Cruising while spotting Roomba

(def-belief-action ba-alt-low
  "I am too low - I don't see Roomba yet"
  (fn [{:keys [altitude]}] (< altitude cruising-altitude))
  (fn [navdata] (drone :up 0.2)))

(def-belief-action ba-alt-ok
  "I am at cruising altitude - I don't see Roomba yet"
  (fn [{:keys [altitude]}] (> altitude cruising-altitude))
  (fn [navdata] (drone :up 0.2)))

(def-goal g-spot-roomba
  "I want to hover at cruising altitude to watch for Roomba"
  (fn [{:keys [targets-num]}] (= targets-num 1))
  [ba-alt-low ba-alt-ok])



;;  Dancing until the dance is over

(def-belief-action ba-dancing
  "The Roomba is still dancing with me!"
  (fn [navdata] (not @dance-over))
  nil)

(def-goal g-dance
  "I want to dance with the Roomba."
  (fn [navdata] @dance-over)
  [ba-dancing])

;; Landing

(def-belief-action ba-flying
  "I am flying"
  (fn [{:keys [control-state]}] (or (= control-state :hovering) (= control-state :flying)))
  (fn [navdata] (do
                 (drone :land))))

(def-belief-action ba-landing
  "I am landing"
  (fn [{:keys [control-state]}] (= control-state :trans-landing))
  nil)

(def-goal g-land
  "I want to land"
  (fn [{:keys [control-state]}] (= control-state :landed))
  [ba-flying ba-landing])


;;;; define the goal list

(set-current-goal-list [g-take-off g-spot-roomba g-dance g-land])


