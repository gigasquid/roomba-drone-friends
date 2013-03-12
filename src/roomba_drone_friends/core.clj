(ns roomba-drone-friends.core
  (:require [clj-drone.core :refer :all]
            [roomba-drone-friends.roomba-music-player :refer :all])
   (:import roombacomm.RoombaCommSerial))


(def roomba (RoombaCommSerial. ))
(map println (.listPorts roomba))
(def portname "/dev/cu.FireFly-943A-SPP-2")
(.connect roomba portname)
(.startup roomba)
(.control roomba)
(.updateSensors roomba)
(.modeAsString roomba)
(.sensorsAsString roomba)

(roomba-music-player roomba put-on-your-sunday-clothes)

(defn setup-drone []
  (drone-initialize)
  (drone :emergency)
  (drone :init-targeting)
  (drone :target-roundel-v)
  (drone :hover-on-roundel))


(defn roomba-drone-dance []
  (setup-drone)
 ; (roomba-music-player roomba put-on-your-sunday-clothes)
  (Thread/sleep 2000)
  (drone :take-off)
  (Thread/sleep 8000)
;  (drone-do-for 5 :up 0.3)
  (.drive roomba 200 400)
  (Thread/sleep 9000)
  (.drive roomba 200 -400)
  (Thread/sleep 9000)
  (.stop roomba)
  (Thread/sleep 3000)
  (drone :land))

(roomba-drone-dance)


