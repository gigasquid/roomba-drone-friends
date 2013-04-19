(defproject roomba-drone-friends "0.1.0-SNAPSHOT"
  :description "Roomba and Drone are Friends"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-drone "0.1.3"]
                 [roombacomm "0.96"]
                 [rxtxcomm "0103"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail javax.jms/jms com.sun.jdmk/jmxtools com.sun.jmx/jmxri]]
                 [org.slf4j/slf4j-log4j12 "1.6.4"]
                 [org.clojure/tools.logging "0.2.3"]
                 [ clj-logging-config "1.9.10"]]
  :jvm-opts [~(str "-Djava.library.path=roombacomm/")])
