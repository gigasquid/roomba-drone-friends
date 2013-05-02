(ns roomba-drone-friends.roomba-dance-beliefs
  (:require[roomba-drone-friends.roomba-goals :refer :all]
           [roomba-drone-friends.roomba-music-player :refer :all]
           [ clj-logging-config.log4j :as log-config]
           [ clojure.tools.logging :as log]
           [ clj-drone.navdata :refer [nav-data]])
  (:import roombacomm.RoombaCommSerial))


(def roomba-agent (agent 0))

(def dr 20)
(def speed 100)
(def pause-time 100)
(def radius (atom 10))
(def dance-over (atom false))
(def roomba-goals (atom []))
(def met-drone (atom false))

(defn do-spiral [roomba]
  (do
    (.drive roomba speed @radius)
    (swap! radius + dr)
    (.pause roomba pause-time)))

(defn meet-drone [roomba]
  (do
    (.goForward roomba)
    (Thread/sleep 8000)
    (.stop roomba)))

;; Trying to find the drone
(defn create-beliefs-goals [roomba]

  (def-belief-action ba-not-met-drone
    "I need to go forward and meet the drone"
    (fn [_] (not @met-drone))
    (fn [navdata] (do
                   (meet-drone roomba)
                   (reset! met-drone true))))


  (def-goal g-meet-drone
    "I want to go forward and meet the drone"
    (fn [_] @met-drone)
    [ba-not-met-drone])

  (def-belief-action ba-see-no-drone
    "The drone does not see me"
    (fn [{:keys [targets-num]}] (not (= targets-num 1)))
    (fn [navdata] (do-spiral roomba)))


  (def-goal g-find-drone-friend
    "I want to find the drone and be friends."
    (fn [{:keys [targets-num]}] (= targets-num 1))
    [ba-see-no-drone])

  (def-belief-action ba-dancing-with-drone
    "I am dancing with the drone"
    (fn [navdata] (not @dance-over))
    (fn [navdata] (do
                   (.stop roomba)
                   (roomba-music-player roomba put-on-your-sunday-clothes)
                   (.drive roomba 200 -400)
                   (Thread/sleep 60000)
                   (.stop roomba)
                   (reset! dance-over true))))


  (def-goal g-drone-dance
    "I want to dance with the drone."
    (fn [navdata] @dance-over)
    [ba-dancing-with-drone])
  (set-roomba-goal-list [g-meet-drone g-find-drone-friend g-drone-dance]))

(defn init-roomba [roomba]
  (create-beliefs-goals roomba))


(defn find-friend [_]
  (log/info (str "dance over " @dance-over))
  (when (not @dance-over)
    (log/info "Find Friend")
    (log/info "doing")
    (eval-roomba-goals @nav-data)
    (Thread/sleep 5000)
    (log/info (log-goal-info))
    (find-friend nil)))

