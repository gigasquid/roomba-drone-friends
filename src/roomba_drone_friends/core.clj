(ns roomba-drone-friends.core
   (:require [clj-drone.core :refer :all])
   (:import roombacomm.RoombaCommSerial))

(println (System/getProperty "java.class.path"))
(println (System/getProperty "java.library.path"))



(def roomba (RoombaCommSerial. ))
(map println (.listPorts roomba))
(def portname "/dev/cu.FireFly-943A-SPP-11")
(.connect roomba portname)
(.startup roomba)
(.control roomba)
(.updateSensors roomba) 



;; put on your sunday clothes
(defn note-length [seconds]
  (int (* seconds 64)))
(defn pause-length [seconds]
  seconds * 1000)

(note-length 1)


(defn quarter-note [num]
  (do
    (.playNote roomba num (note-length 0.25) )
    (Thread/sleep 400)))

(defn half-note [num]
  (do
    (.playNote roomba num (note-length 0.5) )
    (Thread/sleep 800)))

(defn eigth-note [num]
  (do
    (.playNote roomba num (note-length 0.125) )
    (Thread/sleep 200)))

(time (do
   (eigth-note 72)
   (eigth-note 72)
   (eigth-note 72)
   (quarter-note 72)
   (quarter-note 72)

   (quarter-note 72)
   (quarter-note 74)
   (quarter-note 74)
   (quarter-note 77)

   (half-note 77)
   (quarter-note 76)
   (quarter-note 79)


   ))


(drone :emergency)
(drone :flat-trim)
(drone-initialize)
(drone :init-targeting)
(drone :target-roundel-v)
(drone :take-off)
(drone-do-for 4 :up 0.3)
(drone :hover-on-roundel)
(drone :land)



(.pause roomba 30)
(.playNote roomba 79 40)
(.goForward roomba)
(.pause roomba 60)
(.stop roomba)
(.spot roomba)

(Thread/sleep 30000)
(.stop roomba)





(drone :take-off)
(drone :land)




(.pause roomba 30)
(.playNote roomba 72 40)
(.playNote roomba 79 40)
(.spinLeft roomba)
(.spinRight roomba)
(.goBackward roomba)
(.goForward roomba)
(.turnLeft roomba)
(.turnRight roomba)

(.stop roomba)
(.reset roomba)
(.vacuum roomba true)
(.vacuum roomba false)
(.spot roomba)

;; Get the sensor data
(.updateSensors roomba) 
(.bumpLeft roomba)
(.bumpRight roomba)
(.wheelDropLeft roomba)
(.wheelDropRight roomba)
(.wheelDropCenter roomba)
(.sensorsAsString roomba)


(defn bark [r]
  (doto r
    (.vacuum true)
    (.playNote 50 5)
    (.pause 150)
    (.vacuum false)))

(bark roomba)

(.disconnect roomba)

