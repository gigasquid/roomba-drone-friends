(ns roomba-drone-friends.roomba-dance-beliefs
  (:require[roomba-drone-friends.roomba-goals :refer :all]
           [roomba-drone-friends.roomba-music-player :refer :all]
           [ clj-logging-config.log4j :as log-config]
           [ clojure.tools.logging :as log])
  (:import roombacomm.RoombaCommSerial))

(defn init-logger []
  (log-config/set-logger! :level :debug
                          :out "logs/roomba.log"))
(init-logger)

(def nav-data (atom {:targets-num 0}))


(def roomba (RoombaCommSerial. ))
(map println (.listPorts roomba))
(def portname "/dev/cu.FireFly-943A-SPP-2")
(.connect roomba portname)
(.startup roomba)
(.control roomba)
(.updateSensors roomba)
(.modeAsString roomba)
(.sensorsAsString roomba)

(def roomba-agent (agent 0))

(def dr 20)
(def speed 100)
(def pause-time 100)
(def radius (atom 10))
(def dance-over (atom false))
(def roomba-goals (atom []))

(defn do-spiral []
  (do
    (.drive roomba speed @radius)
    (swap! radius + dr)
    (.pause roomba pause-time)))

;; Trying to find the drone
(def-belief-action ba-see-no-drone
  "The drone does not see me"
  (fn [{:keys [targets-num]}] (not (= targets-num 1)))
  (fn [navdata] (do-spiral)))


(def-goal g-find-drone-friend
  "I want to find the drone and be friends."
  (fn [{:keys [targets-num]}] (= targets-num 1))
  [ba-see-no-drone])

(def-belief-action ba-dancing-with-drone
  "I am dancing with the drone"
  (fn [navdata] (not @danceover))
  (fn [navdata] (do
                 (.stop roomba)
                 (roomba-music-player roomba put-on-your-sunday-clothes)
                 (.drive roomba 200 -400)
                 (Thread/sleep 60000)
                 (.stop roomba)
                 (reset! dance-over? true))))


(def-goal g-drone-dance
  "I want to dance with the drone."
  (fn [navdata] @dance-over?)
  [ba-dancing-with-drone])

(set-roomba-goal-list [g-find-drone-friend g-drone-dance])

(reset! dance-over false)

(defn find-friend [_]
  (log/info (str "dance over " @dance-over))
  (when (not @dance-over)
    (log/info "Find Friend")
    (log/info "doing")
    (eval-roomba-goals @nav-data)
    (Thread/sleep 5000)
    (log/info (log-goal-info)))
    (find-friend nil))

(defn test-friend [_]
  (log/info "George"))

(log/info "Hey")


(send-off roomba-agent find-friend)
(send-off roomba-agent test-friend)
@dance-over?

@roomba-goal-list
(eval-roomba-goals @nav-data)

(.stop roomba)

@roomba-agent