(ns roomba-drone-friends.example.moves
  (:require [clj-drone.core :refer :all]
             [clj-drone.navdata :refer :all]))

(drone-initialize)
(drone :emergency)
;Use ip and port for non-standard drone ip/port
;(initialize ip port)
(drone :take-off)
(drone-init-navdata)
(end-navstream)

(drone :anim-double-phi-theta-mixed)
(drone :anim-wave)
(drone :anim-turnaround)
(drone :anim-flip-right)

(drone-do-for 2 :up 0.3)
(drone-do-for 6 :fly 0.05 0 0 0.8)
                                        ; sprial
(drone :hover)
(drone :land)