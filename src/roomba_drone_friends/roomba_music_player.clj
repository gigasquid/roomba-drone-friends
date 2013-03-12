(ns roomba-drone-friends.roomba-music-player)

;; Notes match iRobot specs
;; http://www.irobot.com/images/consumer/hacker/roomba_sci_spec_manual.pdf
(def notes {:As 70
            :B 71
            :C 72
            :Cs 73
            :D 74
            :Ds 75
            :E 76
            :F 77
            :G 79
             })

(defn note-length [seconds]
  (int (* seconds 64)))

(defn pause-length [seconds]
  seconds * 1000)

(defn quarter-note [roomba num]
  (do
    (.playNote roomba num (note-length 0.25) )
    (Thread/sleep 400)))

(defn half-note [roomba num]
  (do
    (.playNote roomba num (note-length 0.5) )
    (Thread/sleep 800)))

(defn eighth-note [roomba num]
  (do
    (.playNote roomba num (note-length 0.125) )
    (Thread/sleep 200)))

(defn whole-note [roomba num]
  (do
    (.playNote roomba num (note-length 1) )
    (Thread/sleep 1600)))

(defn quarter-whole-note [roomba num]
  (do
    (.playNote roomba num (note-length 1.25) )
    (Thread/sleep 2000)))

(defn whole-whole-note [roomba num]
  (do
    (.playNote roomba num (note-length 2) )
    (Thread/sleep 3200)))


(defn roomba-music-player [roomba song]
  (doseq [note song]
    ((first note) roomba (notes (last note)))))

(def put-on-your-sunday-clothes
  [
   [eighth-note :C]  [eighth-note :C]  [eighth-note :C] [quarter-note :C] [quarter-note :C]
   [quarter-note :C]  [quarter-note :D]  [quarter-note :D]  [quarter-note :F]
   [half-note :F]  [quarter-note :E]  [quarter-whole-note :G]
   [eighth-note :C]  [eighth-note :C]  [eighth-note :C] [quarter-note :C] [quarter-note :C]
   [quarter-note :C]  [quarter-note :D] [quarter-note :F] [quarter-note :E]
   [whole-whole-note :As]
   ])

