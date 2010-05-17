(ns amoebas.neat.link-gene)

(defrecord link-gene
  [from to weight enabled? recurrent? innovation-id])

(defn make-link-gene
  ([from to enabled? tag weight]
     (make-link-gene from to enabled? tag weight false))
  ([from to enabled? tag weight recurrent?]
     (do (assert (and (not (nil? from))
                      (not (nil? to))))

         (new link-gene
              from to weight enabled? recurrent? tag))))
