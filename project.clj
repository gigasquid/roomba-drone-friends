(defproject roomba-drone-friends "0.1.0-SNAPSHOT"
  :description "Roomba and Drone are Friends"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-drone "0.1.3"]
                 [roombacomm "0.96"]
                 [rxtxcomm "0103"]]
  :jvm-opts [~(str "-Djava.library.path=roombacomm/")])
