(ns roomba-drone-friends.core
  (:require [clj-drone.core :refer :all]
            [clj-drone.navdata :refer :all]
            [roomba-drone-friends.roomba-music-player :refer :all]
            [roomba-drone-friends.drone-dance-beliefs :refer :all]
            [roomba-drone-friends.roomba-dance-beliefs :refer :all]
            [ clj-logging-config.log4j :as log-config]
           [ clojure.tools.logging :as log] )
  (:import roombacomm.RoombaCommSerial))

(log-config/set-loggers!
 "roomba-drone-friends.roomba-dance-beliefs"
 {:level :debug :out "logs/roomba.log"}

 "clj-drone.core"
 {:level :debug :out "logs/drone.log"})


;;; connect up to the Roobma
(def roomba (RoombaCommSerial. ))
(map println (.listPorts roomba))
(def portname "/dev/cu.FireFly-943A-SPP-2")
(.connect roomba portname)
(.startup roomba)
(.control roomba)
(.updateSensors roomba)
(.modeAsString roomba)
(.sensorsAsString roomba)
(.playNote roomba 72 40)
(init-roomba roomba)



;; practice your song
(roomba-music-player roomba put-on-your-sunday-clothes)


;; Setup the drone
(defn setup-drone []
  (do
    (drone-initialize)
    (drone :emergency)
    (drone :init-targeting)
    (drone :target-roundel-v)
    (drone :hover-on-roundel)
    ))

(drone-init-navdata)
(send-off roomba-agent find-friend)

;; debugging stuff
(agent-errors roomba-agent)
(restart-agent roomba-agent 0)
(@dance-over)
(reset! dance-over true)
(reset! dance-over false)D
(.stop roomba)
(end-navstream)
(agent-errors nav-agent)
(restart-agent nav-agent {})
(drone :land)
@current-goal-list
@nav-data



