(def cat "cat")
;=> "cat"

(defn say-hello [name]
  (str "hello " name))

(say-hello "roomba")
;=> "hello roomba"

(class "roomba")
 ;=> java.lang.String

(.toUpperCase "roomba")
;=> "ROOMBA"
