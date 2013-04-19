(ns roomba-drone-friends.roomba-goals)

(def roomba-belief (atom "None"))
(def roomba-goal (atom "None"))
(def roomba-goal-list (atom []))

(defmacro def-belief-action [bname belief-str belief-fn action-fn]
  `(def ~bname { :belief-str ~belief-str
                :belief ~belief-fn
                :action ~action-fn}))

(defmacro def-goal [gname goal-str goal-fn belief-actions]
  `(def ~gname { :goal-str ~goal-str
                :goal ~goal-fn
                :belief-actions ~belief-actions}))

(defn eval-belief-action [{:keys [belief-str belief action]} navdata]
  (when (belief navdata)
    (reset! roomba-belief belief-str)
    (when action (action navdata))))

(defn eval-goal [{:keys [goal-str goal belief-actions]} navdata]
  (when goal
    (reset! roomba-goal goal-str)
    (if (goal navdata)
      (do
        (reset! roomba-belief (str "Achieved goal: " goal-str))
        :goal-reached)
      (doseq [ba belief-actions]
        (eval-belief-action ba navdata)))))

(defn eval-goal-list [goal-list navdata]
  (if (= :goal-reached (eval-goal (first goal-list) navdata))
    (rest goal-list)
    goal-list))

(defn set-roomba-goal-list [goal-list]
  (reset! roomba-goal-list goal-list))

(defn eval-roomba-goals [navdata]
  (set-roomba-goal-list (eval-goal-list @roomba-goal-list navdata)))

(defn log-goal-list [goal-list]
  (apply str (interpose ", " (map :goal-str goal-list))))

(defn log-goal-info []
  (str "goal list: " (log-goal-list @roomba-goal-list)
       " roomba-goal: " @roomba-goal
       " roomba-belief: " @roomba-belief))