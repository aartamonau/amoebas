(ns amoebas.neat.link-gene)

(defstruct link-gene
  :from
  :to
  :weight
  :enabled?
  :recurrent?
  :innovation-id)

(defn make-link-gene
  ([from to enabled? tag weight]
     (make-link-gene from to enabled? tag weight false))
  ([from to enabled? tag weight recurrent?]
     (do (assert (and (not (nil? from))
                      (not (nil? to))))

         (struct-map link-gene
           :from from
           :to to
           :enabled? enabled?
           :innovation-id tag
           :weight weight
           :recurrent? recurrent?))))
